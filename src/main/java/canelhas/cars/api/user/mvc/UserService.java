package canelhas.cars.api.user.mvc;

import canelhas.cars.api.user.domain.RegistrationDto;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.exception.NotFoundException;
import canelhas.cars.common.functional.Flux;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User register( RegistrationDto request ) {
        // TODO: 16/05/2021 friendly exception messages
        return Flux.of( RegistrationDto::toEntity )
                   .andThen( userRepository::save )
                   .apply( request );
    }


    //region login
    public User find( CPF cpf, EmailAddress email ) {
        return search( cpf, email )
                       .orElseThrow( ( ) -> new NotFoundException( "Não foi encontrado usuário com email " + email + " e cpf " + cpf + "." ) );

    }

    private Optional< User > search( CPF cpf, EmailAddress email ) {
        return this.userRepository.findByCpfAndEmail( cpf.value(), email.value() );
    }
    //endregion
}
