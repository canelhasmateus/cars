package canelhas.cars.api.vehicles.model;


import canelhas.cars.api.vehicles.type.ModelBrand;
import canelhas.cars.api.vehicles.type.ModelName;
import canelhas.cars.api.vehicles.type.ModelYear;
import canelhas.cars.common.exception.NotFoundException;
import canelhas.cars.common.type.TypedId;
import canelhas.cars.foreign.fipe.domain.FipeCar;
import canelhas.cars.schema.DatabaseTables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;
import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.MODEL_NOT_FOUND_WITH_ATTRIBUTES;
import static canelhas.cars.api.util.ExceptionMessages.MODEL_NOT_FOUND_WITH_ID;
import static canelhas.cars.common.languaj.Adjectives.lazily;
import static canelhas.cars.schema.DatabaseColumns.*;
import static java.lang.String.format;

@Entity
@Builder( toBuilder = true )
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table( name = DatabaseTables.MODELS )
public class VehicleModel {

    //region fields
    @Id
    @Column( name = MODEL_ID )
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column( name = MODEL_NAME )
    private String name;

    @Column( name = MODEL_YEAR )
    private String year;

    @Column( name = MODEL_BRAND )
    private String brand;
    //endregion

    //region exceptions
    public static Supplier< NotFoundException > notFound( TypedId< VehicleModel > modelId ) {
        return lazily( NotFoundException::new,
                       format( MODEL_NOT_FOUND_WITH_ID, modelId ) );
    }

    public static Supplier< NotFoundException > notFound( ModelBrand brand, ModelName name, ModelYear year ) {
        return lazily( NotFoundException::new,
                       format( MODEL_NOT_FOUND_WITH_ATTRIBUTES, brand, name, year ) );
    }
    //endregion

    //region help
    public TypedId< VehicleModel > typedId( ) {
        return castId( getId() );
    }

    public ModelBrand typedBrand( ) {
        return ModelBrand.of( getBrand() );
    }

    public ModelName typedName( ) {
        return ModelName.of( getName() );
    }

    public ModelYear typedYear( ) {
        return ModelYear.of( getYear() );
    }

    public static TypedId< VehicleModel > castId( Integer id ) {
        return TypedId.of( id );
    }
    //endregion

    @Transient
    private FipeCar car;

    public Optional< FipeCar > getCar( ) {
        return Optional.ofNullable( car );
    }
}
