package canelhas.cars.api.user.domain;


import canelhas.cars.common.functional.Validation;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailLike;
import canelhas.cars.common.type.MajorityDate;
import canelhas.cars.common.type.ProperName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
public class RegistrationRequest {

    private final ProperName   name;
    private final EmailLike    email;
    private final CPF          cpf;
    private final MajorityDate birthday;

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public RegistrationRequest( @JsonProperty( "name" ) @NotNull String name,
                                @JsonProperty( "email" ) @NotNull String email,
                                @JsonProperty( "cpf" ) @NotNull String cpf,
                                @JsonProperty( "birthday" ) @NotNull Date birthday ) {

        Validation validation = new Validation( HttpStatus.UNPROCESSABLE_ENTITY );

        this.email = validation.map( email, EmailLike::of );
        this.cpf = validation.map( cpf, CPF::of );
        this.name = validation.map( name, ProperName::of );
        this.birthday = validation.map( birthday, MajorityDate::of );

        validation.verify();
    }
}
