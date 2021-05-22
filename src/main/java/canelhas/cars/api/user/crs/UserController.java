package canelhas.cars.api.user.crs;

import canelhas.cars.api.user.domain.UserDto;
import canelhas.cars.common.languaj.noun.Chain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    //region fields
    private final UserService userService;
    //endregion

    @ResponseStatus( HttpStatus.CREATED )
    @PostMapping( "api/users" )
    public UserDto create( @RequestBody UserDto request ) {

        return Chain.of( UserDto::asEntity )
                    .andThen( userService::create )
                    .andThen( UserDto::of )
                    .apply( request );

    }


}
