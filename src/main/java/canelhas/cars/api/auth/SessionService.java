package canelhas.cars.api.auth;

import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.api.auth.domain.CarsCredentials;
import canelhas.cars.api.auth.domain.CarsSession;
import canelhas.cars.api.user.model.User;
import canelhas.cars.api.user.mvc.UserService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static canelhas.cars.api.context.SecurityHolder.getJWTKey;
import static canelhas.cars.api.context.SecurityHolder.getVersion;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserService userService;

    public CarsSession login( CarsCredentials request ) {

        var    user  = userService.find( request.getCpf(), request.getEmail() );
        String token = buildToken( user );

        return CarsSession.builder()
                          .token( token )
                          .build();

    }

    private static String buildToken( User user ) {

        CarsClaims claims = CarsClaims.builder()
                                      .version( getVersion() )
                                      .id( user.getId() )
                                      .name( user.getName() )
                                      .email( user.getEmail() )
                                      .roles( List.of( Authorization.Roles.USER ) )
                                      .build();

        return Jwts.builder()
                   .setClaims( claims )
                   .signWith( HS512, getJWTKey() )
                   .compact();

    }


}
