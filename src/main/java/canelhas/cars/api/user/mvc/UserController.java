package canelhas.cars.api.user.mvc;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.api.auth.SessionService;
import canelhas.cars.api.user.domain.RegistrationRequest;
import canelhas.cars.api.user.domain.UserDto;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Flux;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

import static canelhas.cars.api.auth.Authorization.Roles.USER;

@RestController
public class UserController {


    //region initialization
    private final UserService    userService;
    private final SessionService sessionService;

    public UserController( UserService userService,
                           SessionService sessionService ) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    //endregion

    @PostMapping( "/users" )
    public UserDto register( @RequestBody RegistrationRequest request ) {
        //region definitions
        Function< User, UserDto > parseResponse = user -> new UserDto();
        //endregion

        return Flux.of( userService::register )
                   .andThen( parseResponse )
                   .apply( request );

    }

    @PatchMapping( "/users/current" )
    @Authorization( USER )
    public UserDto edit( @RequestBody UserDto request ) {
        return null;
    }


}
