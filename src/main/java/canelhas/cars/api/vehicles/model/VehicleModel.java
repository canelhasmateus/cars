package canelhas.cars.api.vehicles.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static canelhas.cars.schema.DatabaseColumns.*;

@Entity
@Builder( toBuilder = true )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleModel {
    //FIXME : CREATE CUSTOM SERIALIZERS
    //region fields
    @Id
    @Column( name = MODEL_ID )
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Integer id;

    @Column( name = MODEL_NAME )
    private String name;

    @Column( name = MODEL_YEAR )
    private String year;

    @Column( name = MODEL_BRAND )
    private String brand;
    //endregion

}
