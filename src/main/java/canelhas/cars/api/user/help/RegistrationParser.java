package canelhas.cars.api.user.help;

import canelhas.cars.api.user.domain.RegistrationRequest;
import canelhas.cars.api.user.model.User;

public class RegistrationParser {

//    could make it implement Function. Don't like to because it adds boilerplate and reduces versatility:
//    implementing functional interfaces makes the function non-overloadable,
//    while only gaining the ability to chain it without function ::references.

    public User apply( RegistrationRequest request ) {

        // TODO: 15/05/2021 mapstruct
        //region definitions
        var cpf      = request.getCpf().value();
        var birthday = request.getBirthday().value();
        var email    = request.getEmail().value();
        var name     = request.getName().value();
        //endregion

        return User.builder()
                   .name( name ).birthday( birthday )
                   .email( email ).cpf( cpf )
                   .build();


    }
}
