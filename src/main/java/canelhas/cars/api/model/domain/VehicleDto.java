package canelhas.cars.api.model.domain;

import canelhas.cars.api.model.model.Vehicle;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.functional.Validation;
import canelhas.cars.common.type.ModelYear;
import canelhas.cars.common.type.TypedId;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static canelhas.cars.common.exception.ExceptionMessages.BRAND_REQUIRED;
import static canelhas.cars.common.exception.ExceptionMessages.MODEL_REQUIRED;
import static canelhas.cars.common.functional.Adjectives.lazily;

@Builder( toBuilder = true )
@Getter
@AllArgsConstructor
public class VehicleDto {

    //region fields
    private final String    brand;
    private final String    model;
    private final ModelYear year;

    @Setter
    @JsonIgnore
    private TypedId< User > userId;
    //endregion

    //region constructor
    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public VehicleDto( @JsonProperty( "brand" ) @NotNull String brand,
                       @JsonProperty( "model" ) @NotNull String model,
                       @JsonProperty( "year" ) @NotNull String year ) {

        //region definitions
        UnaryOperator< String > checkBrand = in -> Optional.ofNullable( in )
                                                           .orElseThrow( brandIsRequired() );
        UnaryOperator< String > checkModel = in -> Optional.ofNullable( in )
                                                           .orElseThrow( modelIsRequired() );
        // TODO: 16/05/2021 surely its possible to get these parameters from the annotations;
        //endregion

        var validation = new Validation( DomainException::new );

        this.brand = validation.assemble( brand, checkBrand );
        this.model = validation.assemble( model, checkModel );
        this.year = validation.assemble( year, ModelYear::of );

        validation.verify();
    }
    //endregion

    //region mappings
    public static Vehicle toEntity( VehicleDto dto ) {
        //region definitions
        final var model = dto.getModel();
        final var value = dto.getYear().value();
        final var brand = dto.getBrand();
        //endregion
        return Vehicle.builder()
                      .brand( brand )
                      .model( model )
                      .year( value )
                      .build();
    }


    public static VehicleDto fromEntity( Vehicle vehicle ) {
        //region definitions
        final var year  = ModelYear.of( vehicle.getYear() );
        final var brand = vehicle.getBrand();
        final var model = vehicle.getModel();
        //endregion
        return VehicleDto.builder()
                         .brand( brand )
                         .model( model )
                         .year( year )
                         .build();
    }
    //endregion

    //region exception
    public static Supplier< DomainException > modelIsRequired( ) {
        return lazily( DomainException::new, MODEL_REQUIRED );
    }

    public static Supplier< DomainException > brandIsRequired( ) {
        return lazily( DomainException::new, BRAND_REQUIRED );
    }
    //endregion

}
