package canelhas.cars.api.vehicles.model;

import canelhas.cars.api.user.model.User;
import canelhas.cars.schema.DatabaseTables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static canelhas.cars.schema.DatabaseColumns.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( name = DatabaseTables.VEHICLE )
public class Vehicle {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = VEHICLE_ID )
    private Integer id;

    @JoinColumn( name = USER_ID )
    @ManyToOne( targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH )
    private User owner;

    @JoinColumn( name = MODEL_ID )
    @ManyToOne( targetEntity = VehicleModel.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    private VehicleModel model;

}
