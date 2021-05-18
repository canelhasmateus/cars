package canelhas.cars.api.vehicles.crs;

import canelhas.cars.api.vehicles.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository< Vehicle, Integer > {
    List< Vehicle > findAllByOwnerId( Integer value );
}
