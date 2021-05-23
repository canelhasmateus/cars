package canelhas.cars.api.vehicles.crs;

import canelhas.cars.api.queue.domain.Published;
import canelhas.cars.api.user.crs.UserService;
import canelhas.cars.api.user.model.User;
import canelhas.cars.api.vehicles.model.Vehicle;
import canelhas.cars.api.vehicles.model.VehicleModel;
import canelhas.cars.api.vehicles.type.ModelBrand;
import canelhas.cars.api.vehicles.type.ModelName;
import canelhas.cars.api.vehicles.type.ModelYear;
import canelhas.cars.common.languaj.noun.Chain;
import canelhas.cars.common.languaj.noun.FunctionalSupplier;
import canelhas.cars.common.type.Insertion;
import canelhas.cars.common.type.TypedId;
import canelhas.cars.foreign.fipe.csr.FipeClient;
import canelhas.cars.foreign.fipe.domain.FipeBrandRequest;
import canelhas.cars.foreign.fipe.domain.FipeCarRequest;
import canelhas.cars.foreign.fipe.domain.FipeModelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static canelhas.cars.common.languaj.Adjectives.*;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final RestTemplate restTemplate;

    private final UserService userService;

    private final VehicleRepository vehicleRepository;
    private final ModelRepository   modelRepository;


    @Transactional
    @Published
    public Insertion< Vehicle > create( Vehicle vehicle ) {

        return Chain.of( this::fipeHydrate )
                    .andThen( this::manage )
                    .andThen( vehicleRepository::save )
                    .andThen( Insertion::of )
                    .apply( vehicle );

    }

    public List< Vehicle > list( TypedId< User > id ) {

        return Chain.of( vehicleRepository::findAllByOwnerId )
                    .andThen( concurrently( this::fipeHydrate ) )
                    .apply( id.value() );

    }


    //region fipe
    public Vehicle fipeHydrate( Vehicle vehicle ) {

        final var model = Chain.of( Vehicle::getModel )
                               .andThen( this::fipeSearch )
                               .apply( vehicle );

        return vehicle.toBuilder()
                      .model( model )
                      .build();
    }

    private VehicleModel fipeSearch( VehicleModel model ) {
        return fipeSearch( restTemplate, model );
    }

    public static VehicleModel fipeSearch( RestTemplate template, VehicleModel model ) {

        //region definitions
        final var inputBrand = model.typedBrand();
        final var inputName  = model.typedName();
        final var inputYear  = model.typedYear();

        final var brandRequest = FipeBrandRequest.of( inputBrand );
        final var foundBrand   = FipeClient.find( template, brandRequest );


        final var modelRequest = FipeModelRequest.of( foundBrand, inputName, inputYear );
        final var foundModel   = FipeClient.find( template, modelRequest );


        final var carRequest = FipeCarRequest.of( foundModel );
        final var foundCar   = FipeClient.find( template, carRequest );
        //endregion

        return VehicleModel.builder()
                           .brand( foundModel.getBrand().getName() )
                           .name( foundModel.getModel().getName() )
                           .year( foundModel.getYear().getName() )
                           .car( foundCar )
                           .build();
    }
    //endregion

    //region help
    private Vehicle manage( Vehicle vehicle ) {

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

    public VehicleModel find( VehicleModel model ) {
        //region definitions
        var                                findUsingId   = lazily( this::find, model.typedId() );
        FunctionalSupplier< VehicleModel > useAttributes = ( Void v ) -> find( model.typedBrand(), model.typedName(), model.typedYear() );
        //endregion

        return conditionally( findUsingId )
                       .when( model.getId() != null )
                       .orElse( useAttributes )
                       .get();

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
