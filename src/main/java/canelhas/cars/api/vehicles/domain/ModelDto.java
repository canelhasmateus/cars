package canelhas.cars.api.vehicles.domain;

import canelhas.cars.api.vehicles.model.VehicleModel;
import canelhas.cars.api.vehicles.type.ModelBrand;
import canelhas.cars.api.vehicles.type.ModelName;
import canelhas.cars.api.vehicles.type.ModelYear;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.languaj.noun.Validation;
import canelhas.cars.common.type.Price;
import canelhas.cars.foreign.fipe.domain.FipeCar;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import static canelhas.cars.api.util.RotationHelper.isRotatedOut;

@Builder( toBuilder = true )
@Value
public class ModelDto {

    @JsonProperty( KEY_BRAND )
    ModelBrand brand;
    @JsonProperty( KEY_NAME )
    ModelName  name;
    @JsonProperty( KEY_YEAR )
    ModelYear  year;
    @JsonProperty( KEY_PRICE )
    BigDecimal price;
    @JsonProperty( KEY_ON_ROTATION )
    Boolean    onRotation;
    //region constructor

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static ModelDto of( @JsonProperty( KEY_BRAND ) String brand,
                               @JsonProperty( KEY_NAME ) String model,
                               @JsonProperty( KEY_YEAR ) String year ) {

        var validation = new Validation( DomainException::new );

        var modelBrand = validation.check( brand, ModelBrand::of );
        var modelName  = validation.check( model, ModelName::of );
        var modelYear  = validation.check( year, ModelYear::of );

        validation.verify();

        return ModelDto.builder()
                       .brand( modelBrand )
                       .name( modelName )
                       .year( modelYear )
                       .build();
    }
    //endregion

    //region mappings
    public static VehicleModel asEntity( ModelDto dto ) {
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


    public static ModelDto of( VehicleModel model ) {
        //region definitions
        final var year  = ModelYear.of( model.getYear() );
        final var brand = ModelBrand.of( model.getBrand() );
        final var name  = ModelName.of( model.getName() );
        final var price = model.getCar()
                               .map( FipeCar::getPrice )
                               .map( Price::value )
                               .orElse( null );
        //endregion
        return ModelDto.builder()
                       .brand( brand )
                       .name( name )
                       .year( year )
                       .price( price )
                       .onRotation( isRotatedOut( model ) )
                       .build();
    }
    //endregion


    //region keys
    public static final String KEY_BRAND       = "brand";
    public static final String KEY_NAME        = "name";
    public static final String KEY_YEAR        = "year";
    public static final String KEY_ON_ROTATION = "onRotation";
    public static final String KEY_PRICE       = "price";
    //endregion

}
