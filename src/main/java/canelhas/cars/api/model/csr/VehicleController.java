package canelhas.cars.api.model.csr;

import canelhas.cars.api.auth.Authorization;
import canelhas.cars.api.auth.SessionService;
import canelhas.cars.api.model.domain.VehicleDto;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.type.TypedId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static canelhas.cars.api.auth.Authorization.Roles.USER;
import static canelhas.cars.common.utils.TypingHelper.collectively;

@RestController( "api/users/current/vehicles" )
@Authorization( USER )
@RequiredArgsConstructor
public class VehicleController {


    private final VehicleService vehicleService;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public VehicleDto addVehicle( @RequestBody VehicleDto request ) {

        //region definitions
        UnaryOperator< VehicleDto > addUserId = dto -> {
            TypedId< User > id = TypedId.of( User.class,
                                             SessionService.getCurrentSession().getId() );
            dto.setUserId( id );
            return dto;
        };
        //endregion

        return Chain.of( addUserId )
                    .andThen( vehicleService::register )
                    .andThen( VehicleDto::fromEntity )
                    .apply( request );

    }

    @GetMapping
    public List< VehicleDto > getVehicles( ) {

        //region definitions
        Function< Integer, TypedId< User > > toUserId      = id -> TypedId.of( User.class, id );
        Integer                              currentUserId = SessionService.getCurrentSession().getId();
        //endregion

        return Chain.of( toUserId )
                    .andThen( vehicleService::read )
                    .andThen( collectively( VehicleDto::fromEntity ) )
                    .apply( currentUserId );

    }


}
