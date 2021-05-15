package canelhas.cars.api.user.mvc;

import canelhas.cars.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository< User, Integer > {
}
