package canelhas.cars.api.user.csr;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.api.auth.SessionService;
import canelhas.cars.api.model.domain.VehicleDto;
import canelhas.cars.api.user.domain.RegistrationDto;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Flux;
import canelhas.cars.common.type.TypedId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static canelhas.cars.api.auth.Authorization.Roles.USER;

@RestController
@RequiredArgsConstructor
public class UserController {

    //region fields
    private final UserService userService;
    //endregion

    @ResponseStatus( HttpStatus.CREATED )
    @PostMapping( "api/users" )
    public RegistrationDto register( @RequestBody RegistrationDto request ) {

        return Flux.of( userService::register )
                   .andThen( RegistrationDto::fromEntity )
                   .apply( request );

    }


}
