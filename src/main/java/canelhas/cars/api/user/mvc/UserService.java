package canelhas.cars.api.user.mvc;

import canelhas.cars.api.user.domain.RegistrationDto;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Flux;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    //region initialization
    private final UserRepository userRepository;

    public UserService( UserRepository userRepository ) {
        this.userRepository = userRepository;

    }
    //endregion

    @Transactional
    public User register( RegistrationDto request ) {

        return Flux.of( RegistrationDto::toEntity )
                   .andThen( userRepository::save )
                   .apply( request );
    }


}
