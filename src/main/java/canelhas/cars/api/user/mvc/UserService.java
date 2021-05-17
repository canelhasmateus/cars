package canelhas.cars.api.user.mvc;

import canelhas.cars.api.user.domain.RegistrationDto;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Flux;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailAddress;
import canelhas.cars.common.type.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

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
        return Flux.of( RegistrationDto::toEntity )
                   .andThen( userRepository::save )
                   .apply( request );
    }

    @Transactional
    public Vehicle addVehicle( VehicleDto request ) {

        UnaryOperator< Vehicle > searchFromApi = v -> v;

        return Flux.of( VehicleDto::toEntity )
                   .andThen( searchFromApi )
                   .andThen( vehicleRepository::save )
                   .apply( request );

    }

    public List< Vehicle > getVehicles( Id< User > id ) {
        return Collections.emptyList();
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
