package canelhas.cars.api.model.csr;

import canelhas.cars.api.model.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository< Vehicle, Integer > {
}
