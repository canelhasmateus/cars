package canelhas.cars.api.context;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Flux;
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
import java.util.function.Supplier;

import static canelhas.cars.common.utils.StringHelper.findWith;
import static canelhas.cars.common.utils.TypingHelper.possibly;
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
        Function< HttpServletRequest, Optional< String > > getBearer =
                request -> Optional.ofNullable( request.getHeader( AUTHORIZATION ) );

        //endregion

        try {
            var attributes = RequestContextHolder.currentRequestAttributes();
            return Optional.of( attributes )
                           .map( ServletRequestAttributes.class::cast )
                           .map( ServletRequestAttributes::getRequest )
                           .map( HttpServletRequest.class::cast )
                           .flatMap( getBearer )
                           .flatMap( SecurityHelper::decode );
        }
        catch ( Exception e ) {
            return Optional.empty();
        }
    }

    private static Optional< CarsClaims > decode( String input ) {

        //region definitions
        Function< String, Jws< Claims > > createJws = token -> Jwts.parser()
                                                                   .setSigningKey( getJWTKey() )
                                                                   .parseClaimsJws( token );

        Function< Jws< Claims >, CarsClaims > createCarsClaims = signature -> {
            final var carsClaims = new CarsClaims();
            carsClaims.putAll( signature.getBody() );
            return carsClaims;
        };

        //endregion

        try {

            return Flux.of( findWith( Regexes.BEARER ) )
                       .andThen( possibly( createJws ) )
                       .andThen( possibly( createCarsClaims ) )
                       .apply( input );

        }
        catch ( Exception e ) {
            return Optional.empty();
        }

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
