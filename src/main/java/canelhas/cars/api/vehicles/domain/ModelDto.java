package canelhas.cars.api.vehicles.domain;

import canelhas.cars.api.vehicles.model.VehicleModel;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.functional.Validation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Builder( toBuilder = true )
@Value
public class ModelDto {

    ModelBrand brand;
    ModelName  name;
    ModelYear  year;

    //region constructor

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static ModelDto of( @JsonProperty( "brand" ) @NotNull String brand,
                               @JsonProperty( "name" ) @NotNull String model,
                               @JsonProperty( "year" ) @NotNull String year ) {

        var validation = new Validation( DomainException::new );

        var modelBrand = validation.assemble( brand, ModelBrand::of );
        var modelName  = validation.assemble( model, ModelName::of );
        var modelYear  = validation.assemble( year, ModelYear::of );

        validation.verify();

        return ModelDto.builder()
                       .brand( modelBrand )
                       .name( modelName )
                       .year( modelYear )
                       .build();
    }
    //endregion

    //region mappings
    public static VehicleModel toEntity( ModelDto dto ) {
        //region definitions
        final var model = dto.getName().value();
        final var year  = dto.getYear().value();
        final var brand = dto.getBrand().value();
        //endregion
        return VehicleModel.builder()
                           .brand( brand )
                           .name( model )
                           .year( year )
                           .build();
    }


    public static ModelDto fromEntity( VehicleModel vehicle ) {
        //region definitions
        final var year  = ModelYear.of( vehicle.getYear() );
        final var brand = ModelBrand.of( vehicle.getBrand() );
        final var model = ModelName.of( vehicle.getName() );
        //endregion
        return ModelDto.builder()
                       .brand( brand )
                       .name( model )
                       .year( year )
                       .build();
    }
    //endregion
}
