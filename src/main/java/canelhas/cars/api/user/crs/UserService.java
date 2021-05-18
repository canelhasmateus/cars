package canelhas.cars.api.user.crs;

import canelhas.cars.api.user.domain.UserDto;
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

    private final UserRepository userRepository;

    @Transactional
    public User add( UserDto request ) {
        return Chain.of( UserDto::toEntity )
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
