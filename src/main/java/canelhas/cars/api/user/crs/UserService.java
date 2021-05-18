package canelhas.cars.api.user.crs;

import canelhas.cars.api.model.crs.VehicleRepository;
import canelhas.cars.api.user.domain.RegistrationDto;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    //region fields
    private final UserRepository    userRepository;
    private final VehicleRepository vehicleRepository;
    //endregion

    @Transactional
    public User register( RegistrationDto request ) {
        // TODO: 16/05/2021 friendly exception messages
        return Chain.of( RegistrationDto::toEntity )
                    .andThen( userRepository::save )
                    .apply( request );
    }

    //region read
    public User find( CPF cpf, EmailAddress email ) {

        return search( cpf, email ).orElseThrow( User.notFound( cpf, email ) );

    }

    private Optional< User > search( CPF cpf, EmailAddress email ) {
        return this.userRepository.findByCpfAndEmail( cpf.value(), email.value() );
    }

    //endregion


}
