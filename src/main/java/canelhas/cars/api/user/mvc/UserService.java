package canelhas.cars.api.user.mvc;

import canelhas.cars.api.user.domain.RegistrationRequest;
import canelhas.cars.api.user.help.RegistrationParser;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Flux;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
public class UserService {

    //region initialization
    private final UserRepository userRepository;

    public UserService( UserRepository userRepository ) {
        this.userRepository = userRepository;

    }
    //endregion

    @Transactional
    public User register( RegistrationRequest request ) {

        //region definitions
        Function< RegistrationRequest, User > toModel = new RegistrationParser()::apply;
        //endregion

        return Flux.of( toModel )
//                   .andThen( userRepository::save )
                   .apply( request );
    }


}
