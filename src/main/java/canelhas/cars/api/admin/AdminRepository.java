package canelhas.cars.api.admin;

import canelhas.cars.api.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository< User, Integer > {


    @Modifying
    @Query( value = " TRUNCATE TABLE tb_vehicle" +
                    "", nativeQuery = true )
    void truncateAllVehicles( );

    @Modifying
    @Query( value = " TRUNCATE TABLE tb_user" +
                    "", nativeQuery = true )
    void truncateAllUsers( );

    @Modifying
    @Query( value = " TRUNCATE TABLE tb_model" +
                    "", nativeQuery = true )
    void truncateAllModels();
}
