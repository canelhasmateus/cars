package canelhas.cars.api.auth.domain;

import canelhas.cars.common.functional.Validation;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailAddress;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;


@Getter
public class CarsCredentials {

    private final EmailAddress email;
    private final CPF          cpf;


    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public CarsCredentials( @JsonProperty( "email" ) @NotNull String email,
                            @JsonProperty( "cpf" ) @NotNull String cpf ) {

        var validation = new Validation( HttpStatus.UNPROCESSABLE_ENTITY );

        this.email = validation.map( email, EmailAddress::of );
        this.cpf = validation.map( cpf, CPF::of );

        validation.verify();
    }
}
