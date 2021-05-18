package canelhas.cars.api.aspect;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.api.auth.SessionService;
import canelhas.cars.api.auth.domain.CarsClaims;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static canelhas.cars.common.functional.Adjectives.conditionally;
import static canelhas.cars.common.functional.Verbs.raise;

@Aspect
@Component
public class AuthorizationAdvice {

    @Pointcut( "@annotation( canelhas.cars.api.auth.Authorization )" )
    public void authRequired( ) { }


    @Pointcut( "execution( * canelhas.cars.api..*.*(..) )" )
    public void insideApi( ) { }


    @Before( "insideApi() && authRequired()" )
    public void assertAuthorization( JoinPoint joinPoint ) {

        //region get Annotation
        final var signature  = ( MethodSignature ) joinPoint.getSignature();
        final var method     = signature.getMethod();
        final var annotation = AnnotationUtils.findAnnotation( method, Authorization.class );
        assert annotation != null;
        //endregion

        //region find intersection of authorizations
        // TODO: 17/05/2021  hierarchical access;
        final var requiredRoles = Set.of( annotation.value() );
        final var intersection = SessionService
                                         .getCurrentSession()
                                         .getRoles().stream()
                                         .filter( requiredRoles::contains )
                                         .collect( Collectors.toList() );
        //endregion

        conditionally( raise( CarsClaims.accessDenied() ) )
                .apply( intersection.isEmpty() );

    }

}

