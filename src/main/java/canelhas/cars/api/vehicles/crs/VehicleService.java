package canelhas.cars.api.vehicles.crs;

import canelhas.cars.api.user.model.User;
import canelhas.cars.api.vehicles.domain.*;
import canelhas.cars.api.vehicles.model.Vehicle;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.type.TypedId;
import canelhas.cars.foreign.fipe.csr.FipeClient;
import canelhas.cars.foreign.fipe.domain.FipeBrand;
import canelhas.cars.foreign.fipe.domain.FipeModel;
import canelhas.cars.foreign.fipe.domain.FipeYear;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Function;

import static canelhas.cars.common.functional.Adjectives.partially;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final RestTemplate      restTemplate;

    @Transactional
    public Vehicle add( ModelDto request ) {

        return Chain.of( this::fipeApiSearch )
                    .andThen( VehicleDto::toEntity )
                    .andThen( vehicleRepository::save )
                    .apply( request );

    }


    public List< Vehicle > list( TypedId< User > id ) {
        return vehicleRepository.findAllByOwnerId( id.value() );
    }

    private ModelDto fipeApiSearch( ModelDto dto ) {
        //region definitions
        Function< Integer, TypedId< FipeBrand > > toBrandId = partially( TypedId::of, FipeBrand.class );
        Function< Integer, TypedId< FipeModel > > toModelId = partially( TypedId::of, FipeModel.class );
        Function< Integer, TypedId< FipeYear > >  toYearId  = partially( TypedId::of, FipeYear.class );
        //endregion

        final var brand     = FipeClient.search( restTemplate, dto.getBrand() );
        final var brandId   = toBrandId.apply( brand.getCode() );
        final var brandName = ModelBrand.of( brand.getName() );

        final var model     = FipeClient.search( restTemplate, dto.getName(), brandId );
        final var modelId   = toModelId.apply( model.getCode() );
        final var modelName = ModelName.of( model.getName() );

        final var year      = FipeClient.search( restTemplate, dto.getYear(), brandId, modelId );
        final var yearId    = toYearId.apply( year.getCode() );
        final var modelYear = ModelYear.of( year.getName() );


        return dto.toBuilder()
                  .brand( brandName )
                  .name( modelName )
                  .year( modelYear )
                  .build();
    }

}
