package canelhas.cars.api.user.crs;

import canelhas.cars.api.queue.domain.Published;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.languaj.noun.Chain;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailAddress;
import canelhas.cars.common.type.Insertion;
import canelhas.cars.common.type.TypedId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static canelhas.cars.common.languaj.Verbs.raise;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    @Published
    public Insertion< User > create( User request ) {

        return Chain.of( this::searchDuplicate )
                    .andThen( userRepository::save )
                    .andThen( Insertion::of )
                    .apply( request );
    }

    private User searchDuplicate( User user ) {

        final var cpf   = user.typedCpf();
        final var email = user.typedEmail();

        search( cpf ).ifPresent( then -> raise( User.alreadyExistsWith( cpf ) ) );
        search( email ).ifPresent( then -> raise( User.alreadyExistsWith( email ) ) );

        return user;

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

    private Optional< User > search( EmailAddress email ) {
        return userRepository.findByEmail( email.value() );
    }

    private Optional< User > search( CPF cpf ) {
        return userRepository.findByCpf( cpf.value() );
    }
    //endregion


}
