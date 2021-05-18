package canelhas.cars.api.context;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.api.user.model.User;
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

    private static Optional< CarsClaims > decode( String s ) {

        try {
            final var matcher = Regexes.BEARER.matcher( s );
            matcher.find();
            var token = matcher.group();

            Jws< Claims > claimsJws = Jwts.parser()
                                          .setSigningKey( getJWTKey() )
                                          .parseClaimsJws( token );

            var carsClaims = new CarsClaims();
            carsClaims.putAll( claimsJws.getBody() );
            return Optional.of( carsClaims );
        }
        catch ( Exception e ) {
            return Optional.empty();
        }

    }

    public static String encode( User user ) {

        //region roles
        List< Authorization.Roles > roles = new ArrayList<>();
        roles.add( Authorization.Roles.USER );
        if ( user.getEmail() != null && user.getEmail().contains( "@admin" ) ) {
            roles.add( Authorization.Roles.ADMIN );
        }
        //endregion

        var claims = CarsClaims.builder()
                               .version( getVersion() )
                               .id( user.getId() )
                               .name( user.getName() )
                               .email( user.getEmail() )
                               .roles( roles )
                               .build();

        return Jwts.builder()
                   .setClaims( claims )
                   .signWith( HS512, getJWTKey() )
                   .compact();

    }
}
