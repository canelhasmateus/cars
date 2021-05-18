package canelhas.cars.api.user.mvc;

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
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static canelhas.cars.common.exception.ExceptionMessages.BRAND_REQUIRED;
import static canelhas.cars.common.exception.ExceptionMessages.MODEL_REQUIRED;

@Builder( toBuilder = true )
@Getter
@AllArgsConstructor
public class VehicleDto {

    private final String    brand;
    private final String    model;
    private final ModelYear year;

    @Setter
    @JsonIgnore
    private TypedId< User > userId;

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public VehicleDto( @JsonProperty( "brand" ) @NotNull String brand,
                       @JsonProperty( "model" ) @NotNull String model,
                       @JsonProperty( "year" ) @NotNull String year ) {

        //region definitions
        UnaryOperator< String > checkBrand = in -> Optional.ofNullable( in )
                                                           .orElseThrow( ( ) -> new DomainException( BRAND_REQUIRED ) );
        UnaryOperator< String > checkModel = in -> Optional.ofNullable( in )
                                                           .orElseThrow( ( ) -> new DomainException( MODEL_REQUIRED ) );
        // TODO: 16/05/2021 surely its possible to get these parameters from the annotations;
        //endregion

        var validation = new Validation( DomainException::new );

        this.brand = validation.map( brand, checkBrand );
        this.model = validation.map( model, checkModel );
        this.year = validation.map( year, ModelYear::of );

        validation.verify();
    }

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

    public static List< VehicleDto > fromEntity( List< Vehicle > vehicleList ) {
        return vehicleList.stream()
                          .map( VehicleDto::fromEntity )
                          .collect( Collectors.toList() );
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

}
