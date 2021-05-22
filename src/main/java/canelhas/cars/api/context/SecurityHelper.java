package canelhas.cars.api.context;

import canelhas.cars.api.auth.domain.Authorization.Roles;
import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.utils.Regexes;
import canelhas.cars.common.utils.StringHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static canelhas.cars.common.functional.Adjectives.*;
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
        //endregion

        final var claims = Chain.of( findWith( Regexes.BEARER ) )
                                .andThen( createJws )
                                .andThen( Jwt::getBody )
                                .apply( input );

        final var carsClaims = new CarsClaims();
        carsClaims.putAll( claims );
        return carsClaims;

    }

    public static String encode( User user ) {

        //region definitions
        final var id            = user.getId();
        final var name          = user.getName();
        final var email         = user.getEmail();
        final var containsAdmin = partially( "@admin", StringHelper::contained );

        Predicate< String > isAdmin = s -> Optional.ofNullable( s )
                                                   .map( containsAdmin )
                                                   .orElse( false );

        List< Roles > roles        = new ArrayList<>();
        final var     addAdminRole = lazily( roles::add, Roles.ADMIN );
        //endregion

        roles.add( Roles.USER );
        conditionally( addAdminRole )
                .on( isAdmin.test( email ) );

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
