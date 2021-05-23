package canelhas.cars.api.auth.crs;

import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.api.auth.domain.CarsCredentials;
import canelhas.cars.api.auth.domain.CarsSession;
import canelhas.cars.api.user.crs.UserService;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.languaj.noun.Chain;
import canelhas.cars.common.utils.Regexes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static canelhas.cars.api.auth.domain.Authorization.Roles;
import static canelhas.cars.common.languaj.Adjectives.*;
import static canelhas.cars.common.utils.StringHelper.contains;
import static canelhas.cars.common.utils.StringHelper.findWith;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Service
@RequiredArgsConstructor
public class SessionService {

    public static final String      AUTHORIZATION = "authorization";
    private final       UserService userService;

    private static CarsClaims decode( String input ) {

        //region definitions
        final Function< String, Jws< Claims > > createJws =
                token -> Jwts.parser()
                             .setSigningKey( SecurityHolder.getJWTKey() )
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
        final var id    = user.getId();
        final var name  = user.getName();
        final var email = user.getEmail();
        //endregion

        //region implementation
        final var roles = new ArrayList< Roles >();
        roles.add( Roles.USER );

        final var addAdmin = lazily( roles::add, Roles.ADMIN );
        conditionally( addAdmin )
                .when( contains( email, "@admin" ) )
                .ifPresent( Supplier::get );
        //endregion


        var claims = CarsClaims.builder()
                               .version( SecurityHolder.getVersion() )
                               .id( id )
                               .name( name )
                               .email( email )
                               .roles( roles )
                               .build();

        return Jwts.builder()
                   .setClaims( claims )
                   .signWith( HS512, SecurityHolder.getJWTKey() )
                   .compact();

    }

    public static Optional< CarsClaims > getCurrentClaims( ) {

        //region definitions
        final var toServletRequest = Chain.of( ServletRequestAttributes.class::cast )
                                          .andThen( ServletRequestAttributes::getRequest )
                                          .andThen( HttpServletRequest.class::cast );

        final var parseRequest = Chain.of( toServletRequest )
                                      .andThen( request -> request.getHeader( AUTHORIZATION ) )
                                      .andThen( SessionService::decode );
        //endregion

        return hopefully( parseRequest )
                       .apply( RequestContextHolder.currentRequestAttributes() );

    }

    public CarsSession login( CarsCredentials request ) {

        //region definitions
        final var cpf   = request.getCpf();
        final var email = request.getEmail();
        final var user  = userService.find( cpf, email );
        final var token = encode( user );
        //endregion

        return CarsSession.builder()
                          .token( token )
                          .build();

    }

    //region help
    public static CarsClaims getCurrentSession( ) {
        return getCurrentClaims()
                       .orElseThrow( CarsClaims.notFound() );
    }
    //endregion
}
