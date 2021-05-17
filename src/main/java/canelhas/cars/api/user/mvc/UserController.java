package canelhas.cars.api.user.mvc;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.api.auth.SessionService;
import canelhas.cars.api.user.domain.RegistrationDto;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Flux;
import canelhas.cars.common.type.Id;
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

    @PostMapping( "api/users/current/vehicles" )
    @Authorization( USER )
    @ResponseStatus( HttpStatus.CREATED )
    public VehicleDto addVehicle( @RequestBody VehicleDto request ) {

        //region definitions
        UnaryOperator< VehicleDto > addUserId = dto -> {
            Id< User > id = Id.of( User.class,
                                   SessionService.getCurrentSession().getId() );
            dto.setUserId( id );
            return dto;
        };
        //endregion

        return Flux.of( addUserId )
                   .andThen( userService::addVehicle )
                   .andThen( VehicleDto::fromEntity )
                   .apply( request );

    }

    @GetMapping( "api/users/current/vehicles" )
    @Authorization( USER )
    public List< VehicleDto > getVehicles( ) {

        //region definitions
        Function< Integer, Id< User > > toUserId      = id -> Id.of( User.class, id );
        Integer                         currentUserId = SessionService.getCurrentSession().getId();
        //endregion

        return Flux.of( toUserId )
                   .andThen( userService::getVehicles )
                   .andThen( VehicleDto::fromEntity )
                   .apply( currentUserId );

    }


}
