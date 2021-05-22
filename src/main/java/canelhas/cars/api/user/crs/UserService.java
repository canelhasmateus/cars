package canelhas.cars.api.user.crs;

import canelhas.cars.api.user.model.User;
import canelhas.cars.api.vehicles.crs.Insertion;
import canelhas.cars.api.queue.domain.Published;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailAddress;
import canelhas.cars.common.type.TypedId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    @Published
    public Insertion< User > create( User request ) {
        //region definitions
        final Function< User, User > identity = Function.identity();
        //endregion
        return Chain.of( identity )
                    .andThen( userRepository::save )
                    .andThen( Insertion::of )
                    .apply( request );
    }

    //region read
    public User find( CPF cpf, EmailAddress email ) {

        return search( cpf, email ).orElseThrow( User.notFound( cpf, email ) );

    }

    private Optional< User > search( CPF cpf, EmailAddress email ) {
        return this.userRepository.findByCpfAndEmail( cpf.value(), email.value() );
    }

    public User find( TypedId< User > userId ) {
        return search( userId )
                       .orElseThrow( User.notFound( userId ) );
    }

    private Optional< User > search( TypedId< User > userId ) {

        return userRepository.findById( userId.value() );
    }

    //endregion


}
