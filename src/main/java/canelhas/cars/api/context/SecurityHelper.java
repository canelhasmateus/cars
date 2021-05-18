package canelhas.cars.api.context;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.namespace.Regexes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static canelhas.cars.common.functional.Adjectives.hopefully;
import static canelhas.cars.common.utils.StringHelper.findWith;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;

public class SecurityHelper {

    //region monorepo

    private SecurityHelper( ) {}
    //endregion

    public static final String AUTHORIZATION = "authorization";

    public static String getJWTKey( ) {
        return "carscarscarscars";
    }

    public static String getVersion( ) {
        return "0.0.1";
    }

    public static Optional< CarsClaims > getCurrentClaims( ) {

        //region definitions
        Function< HttpServletRequest, String > getBearer = request -> request.getHeader( AUTHORIZATION );

        final var parseRequest = Chain.of( ServletRequestAttributes.class::cast )
                                      .andThen( ServletRequestAttributes::getRequest )
                                      .andThen( HttpServletRequest.class::cast )
                                      .andThen( getBearer )
                                      .andThen( SecurityHelper::decode );
        //endregion

        return hopefully( parseRequest )
                       .apply( RequestContextHolder.currentRequestAttributes() );

    }

    private static CarsClaims decode( String input ) {

        //region definitions
        final Function< String, Jws< Claims > > createJws = token -> Jwts.parser()
                                                                         .setSigningKey( getJWTKey() )
                                                                         .parseClaimsJws( token );

        final Function< Jws< Claims >, CarsClaims > createCarsClaims = signature -> {
            final var carsClaims = new CarsClaims();
            carsClaims.putAll( signature.getBody() );
            return carsClaims;
        };

        //endregion

        return Chain.of( findWith( Regexes.BEARER ) )
                    .andThen( createJws )
                    .andThen( createCarsClaims )
                    .apply( input );

    }

    public static String encode( User user ) {

        //region definitions
        final var id    = user.getId();
        final var name  = user.getName();
        final var email = user.getEmail();

        List< Authorization.Roles > roles = new ArrayList<>();
        roles.add( Authorization.Roles.USER );
        if ( email != null && email.contains( "@admin" ) ) {
//            fake a lot né kkkkkkkkkkkkkkkkkkkk
            // TODO: 18/05/2021 remove control flow
            roles.add( Authorization.Roles.ADMIN );
        }
        //endregion

        var claims = CarsClaims.builder()
                               .version( getVersion() )
                               .id( id )
                               .name( name )
                               .email( email )
                               .roles( roles )
                               .build();

        return Jwts.builder()
                   .setClaims( claims )
                   .signWith( HS512, getJWTKey() )
                   .compact();

    }
}
