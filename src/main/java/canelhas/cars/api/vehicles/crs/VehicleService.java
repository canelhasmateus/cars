package canelhas.cars.api.vehicles.crs;

import canelhas.cars.api.auth.crs.SessionService;
import canelhas.cars.api.user.crs.UserService;
import canelhas.cars.api.user.model.User;
import canelhas.cars.api.vehicles.domain.*;
import canelhas.cars.api.vehicles.model.Vehicle;
import canelhas.cars.api.vehicles.model.VehicleModel;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.type.TypedId;
import canelhas.cars.foreign.fipe.csr.FipeClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static canelhas.cars.common.functional.Adjectives.*;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final RestTemplate restTemplate;

    private final UserService userService;

    private final VehicleRepository vehicleRepository;
    private final ModelRepository   modelRepository;


    @Transactional
    public Vehicle create( ModelDto request ) {
        //region definitions
        Function< ModelDto, ModelDto > fipeSearch = partially( FipeClient::search, restTemplate );

        final var currentUserId = SessionService.getCurrentSession().getId();
        final var toEntity      = partially( VehicleDto::toEntity, currentUserId );
        //endregion

        return Chain.of( fipeSearch )
                    .andThen( toEntity )
                    .andThen( this::hydrate )
                    .andThen( vehicleRepository::save )
                    .apply( request );

    }

    //region help
    private Vehicle hydrate( Vehicle vehicle ) {

        final var owner = findOwner( vehicle );
        vehicle.setOwner( owner );

        hopefully( this::findModel )
                .apply( vehicle )
                .ifPresent( vehicle::setModel );

        return vehicle;

    }

    private VehicleModel findModel( Vehicle vehicle ) {
        return Chain.of( Vehicle::getModel )
                    .andThen( this::find )
                    .apply( vehicle );
    }

    private User findOwner( Vehicle vehicle ) {
        return Chain.of( Vehicle::getOwner )
                    .andThen( User::typedId )
                    .andThen( userService::find )
                    .apply( vehicle );
    }
    //endregion

    //region read
    public List< Vehicle > list( TypedId< User > id ) {
        return vehicleRepository.findAllByOwnerId( id.value() );
    }

    public VehicleModel find( VehicleModel model ) {

        //region definitions
        var                      findUsingId       = lazily( this::find, model.typedId() );
        Supplier< VehicleModel > orUsingAttributes = ( ) -> find( model.typedBrand(), model.typedName(), model.typedYear() );
        //endregion

        return conditionally( findUsingId, orUsingAttributes )
                       .on( model.getId() != null );

    }

    public Optional< VehicleModel > search( VehicleModel model ) {

        //region definitions
        var                                  searchUsingId     = lazily( this::search, model.typedId() );
        Supplier< Optional< VehicleModel > > orUsingAttributes = ( ) -> search( model.typedBrand(), model.typedName(), model.typedYear() );
        //endregion

        return conditionally( searchUsingId, orUsingAttributes )
                       .on( model.getId() == null );

    }

    public VehicleModel find( TypedId< VehicleModel > modelId ) {

        return search( modelId )
                       .orElseThrow( VehicleModel.notFound( modelId ) );
    }

    public Optional< VehicleModel > search( TypedId< VehicleModel > modelId ) {
        return modelRepository.findById( modelId.value() );
    }

    public VehicleModel find( ModelBrand brand, ModelName name, ModelYear year ) {
        return search( brand, name, year )
                       .orElseThrow( VehicleModel.notFound( brand, name, year ) );

    }

    public Optional< VehicleModel > search( ModelBrand brand, ModelName name, ModelYear year ) {
        return modelRepository.findByBrandAndNameAndYear( brand.value(), name.value(), year.value() );

    }
    //endregion


}
