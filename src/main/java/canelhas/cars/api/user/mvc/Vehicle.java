package canelhas.cars.api.user.mvc;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import static canelhas.cars.schema.DatabaseColumns.*;

@Entity
@Builder( toBuilder = true )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    //region fields
    @Id
    @Column( name = VEHICLE_ID )
    private Integer id;

    @Column( name = VEHICLE_MODEL )
    private String model;

    @Column( name = VEHICLE_YEAR )
    private String year;

    @Column( name = VEHICLE_BRAND )
    private String brand;

    @Transient
//    @Builder.Default
    private Boolean rotatedOut = RotationHelper.isRotatedOut( this );
    //endregion


}
