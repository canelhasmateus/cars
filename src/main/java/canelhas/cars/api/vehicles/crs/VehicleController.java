package canelhas.cars.api.vehicles.crs;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.api.auth.SessionService;
import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.api.user.model.User;
import canelhas.cars.api.vehicles.domain.ModelDto;
import canelhas.cars.api.vehicles.domain.VehicleDto;
import canelhas.cars.api.vehicles.model.Vehicle;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.type.TypedId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

import static canelhas.cars.api.auth.Authorization.Roles.USER;
import static canelhas.cars.common.functional.Adjectives.collectively;
import static canelhas.cars.common.functional.Adjectives.partially;

@RestController
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping( "api/users/current/vehicles" )
    @ResponseStatus( HttpStatus.CREATED )
    @Authorization( USER )
    public VehicleDto add( @RequestBody ModelDto request ) {
        return Chain.of( vehicleService::add )
                    .andThen( VehicleDto::fromEntity )
                    .apply( request );

    }

    @GetMapping( "api/users/current/vehicles" )
    @Authorization( USER )
    public List< ModelDto > list( ) {
        //region definitions
        Function< Integer, TypedId< User > > typeId = partially( TypedId::of, User.class );
        final var createDto = Chain.of( Vehicle::getModel )
                                   .andThen( ModelDto::fromEntity );
        //endregion

        return Chain.of( CarsClaims::getId )
                    .andThen( typeId )
                    .andThen( vehicleService::list )
                    .andThen( collectively( createDto ) )
                    .apply( SessionService.getCurrentSession() );

    }


}
