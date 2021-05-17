package canelhas.cars.api.auth;

import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.api.auth.domain.CarsCredentials;
import canelhas.cars.api.auth.domain.CarsSession;
import canelhas.cars.api.context.SecurityHelper;
import canelhas.cars.api.user.mvc.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserService userService;

    public static CarsClaims getCurrentSession( ) {
        return SecurityHelper.getCurrentClaims()
                             .orElseThrow( CarsClaims.notFound() );
    }

    public CarsSession login( CarsCredentials request ) {

        //region definitions
        final var cpf   = request.getCpf();
        final var email = request.getEmail();
        final var user  = userService.find( cpf, email );
        final var token = SecurityHelper.encode( user );
        //endregion

        return CarsSession.builder()
                          .token( token )
                          .build();

    }


}
