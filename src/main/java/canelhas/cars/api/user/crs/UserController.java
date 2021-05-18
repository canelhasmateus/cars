package canelhas.cars.api.user.crs;

import canelhas.cars.api.user.domain.RegistrationDto;
import canelhas.cars.common.functional.Chain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    //region fields
    private final UserService userService;
    //endregion

    @ResponseStatus( HttpStatus.CREATED )
    @PostMapping( "api/users" )
    public RegistrationDto register( @RequestBody RegistrationDto request ) {

        return Chain.of( userService::register )
                    .andThen( RegistrationDto::fromEntity )
                    .apply( request );

    }


}
