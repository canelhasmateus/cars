package canelhas.cars.api.auth;

import canelhas.cars.api.auth.domain.CarsCredentials;
import canelhas.cars.api.auth.domain.CarsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SessionController {


    private final SessionService sessionService;

    @PostMapping( "/api/login" )
    public CarsSession login( @RequestBody CarsCredentials request ) {

        return sessionService.login( request );
    }

}
