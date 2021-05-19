package canelhas.cars.api.vehicles.crs;

import canelhas.cars.api.vehicles.model.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository< VehicleModel, Integer > {
    Optional< VehicleModel > findByBrandAndNameAndYear( String brand, String name, String year );
}
