package canelhas.cars.api.user.mvc;

import canelhas.cars.api.user.domain.RegistrationDto;
import canelhas.cars.common.functional.Flux;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping( "api/users" )
    public RegistrationDto register( @RequestBody RegistrationDto request ) {

        return Flux.of( userService::register )
                   .andThen( RegistrationDto::fromEntity )
                   .apply( request );

    }

//    @PatchMapping( "/users/current" )
//    @Authorization( USER )
//    public UserDto edit( @RequestBody UserDto request ) {
//        return null;
//    }


}
