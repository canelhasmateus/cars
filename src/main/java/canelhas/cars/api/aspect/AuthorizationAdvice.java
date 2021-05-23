package canelhas.cars.api.aspect;

import canelhas.cars.api.auth.crs.SessionService;
import canelhas.cars.api.auth.domain.Authorization;
import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.common.languaj.noun.Chain;
import canelhas.cars.common.utils.AspectHelper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static canelhas.cars.common.languaj.Adjectives.hopefully;
import static canelhas.cars.common.languaj.Adverbs.conditionally;
import static canelhas.cars.common.languaj.Verbs.raise;
import static canelhas.cars.common.utils.AspectHelper.findAnnotation;

@Aspect
@Component
public class AuthorizationAdvice {

    @Pointcut( "@annotation( canelhas.cars.api.auth.domain.Authorization )" )
    public void authRequired( ) { }

    @Before( "authRequired()" )
    public void assertAuthorization( JoinPoint joinPoint ) {

        //region definitions
        final var findRequiredRoles = Chain.of( AspectHelper::getMethod )
                                           .andThen( findAnnotation( Authorization.class ) )
                                           .andThen( Authorization::value )
                                           .andThen( Set::of );
        //endregion

        //region implementation
        // TODO: 17/05/2021  hierarchical access;
        final var requiredPermissions = hopefully( findRequiredRoles )
                                                .apply( joinPoint )
                                                .orElseGet( Collections::emptySet );

        final var currentPermissions = SessionService.getCurrentSession()
                                                     .getRoles();

        final var sufficientPermissions = currentPermissions.stream()
                                                            .filter( requiredPermissions::contains )
                                                            .collect( Collectors.toSet() );
        //endregion

        conditionally( raise( CarsClaims.accessDenied() ) )
                .on( sufficientPermissions.isEmpty() );

    }

}

