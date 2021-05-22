package canelhas.cars.api.auth.domain;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.languaj.noun.Validation;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailAddress;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;


@Builder( toBuilder = true )
@Value
public class CarsCredentials {

    private final EmailAddress email;
    private final CPF          cpf;


    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static CarsCredentials of( @JsonProperty( "email" ) String email,
                                      @JsonProperty( "cpf" ) String cpf ) {

        //region definitions
        final var validation   = new Validation( DomainException::new );
        final var emailAddress = validation.check( email, EmailAddress::of );
        final var betterCpf    = validation.check( cpf, CPF::of );
        validation.verify();
        //endregion

        return CarsCredentials.builder()
                              .email( emailAddress )
                              .cpf( betterCpf )
                              .build();
    }
}
