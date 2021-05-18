package canelhas.cars.api.user.crs;

import canelhas.cars.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository< User, Integer > {
    Optional< User > findByCpfAndEmail( String cpf, String email );
}
