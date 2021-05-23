package canelhas.cars.api.vehicles.crs;

import canelhas.cars.api.auth.crs.SessionService;
import canelhas.cars.api.auth.domain.Authorization;
import canelhas.cars.api.auth.domain.CarsClaims;
import canelhas.cars.api.vehicles.domain.ModelDto;
import canelhas.cars.api.vehicles.domain.VehicleDto;
import canelhas.cars.api.vehicles.model.Vehicle;
import canelhas.cars.common.languaj.noun.Chain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

import static canelhas.cars.api.auth.domain.Authorization.Roles.USER;
import static canelhas.cars.common.languaj.Adjectives.collectively;

@RestController
@RequiredArgsConstructor
@Authorization( USER )
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping( "api/users/current/vehicles" )
    @ResponseStatus( HttpStatus.CREATED )
    public VehicleDto create( @RequestBody ModelDto request ) {
        //region definitions
        final var currentUserId = SessionService.getCurrentSession().getId();
        final var createEntity = Chain.of( VehicleDto::asEntity )
                                      .partialize( currentUserId );
        //endregion

        return Chain.of( createEntity )
                    .andThen( vehicleService::create )
                    .andThen( VehicleDto::of )
                    .apply( request );

    }

    @GetMapping( "api/users/current/vehicles" )
    public List< ModelDto > list( ) {
        //region definitions
        Function< Vehicle, ModelDto > createDto = Chain.of( Vehicle::getModel )
                                                       .andThen( ModelDto::of );
        //endregion

        return Chain.of( CarsClaims::getId )
                    .andThen( vehicleService::list )
                    .andThen( collectively( createDto ) )
                    .apply( SessionService.getCurrentSession() );

    }


}
