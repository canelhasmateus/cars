package canelhas.cars.api.user.domain;


import canelhas.cars.api.user.model.User;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.functional.Validation;
import canelhas.cars.common.type.AdultBirthday;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailAddress;
import canelhas.cars.common.type.ProperName;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Builder( toBuilder = true )
@AllArgsConstructor
public class RegistrationDto {


    @JsonProperty( "id" )
    private final Integer id;

    @JsonProperty( "name" )
    private final ProperName name;

    @JsonProperty( "email" )
    private final EmailAddress email;

    @JsonProperty( "cpf" )
    private final CPF cpf;

    @JsonProperty( "birthday" )
    private final AdultBirthday birthday;

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public RegistrationDto( @JsonProperty( "name" ) @NotNull String name,
                            @JsonProperty( "email" ) @NotNull String email,
                            @JsonProperty( "cpf" ) @NotNull String cpf,
                            @JsonProperty( "birthday" ) @NotNull @JsonFormat(
                                    pattern = "yyyy-MM-dd",
                                    timezone = "Brazil/East" ) Date birthday ) {

        var validation = new Validation( DomainException::new );

        this.id = null;
        this.email = validation.map( email, EmailAddress::of );
        this.cpf = validation.map( cpf, CPF::of );
        this.name = validation.map( name, ProperName::of );
        this.birthday = validation.map( birthday, AdultBirthday::of );

        validation.verify();
    }


    public static User toEntity( RegistrationDto request ) {

        //region definitions
        final var id       = request.getId();
        final var cpf      = request.getCpf().value();
        final var birthday = request.getBirthday().value();
        final var email    = request.getEmail().value();
        final var name     = request.getName().value();
        //endregion

        return User.builder()
                   .id( id )
                   .name( name ).birthday( birthday )
                   .email( email ).cpf( cpf )
                   .build();


    }


    public static RegistrationDto fromEntity( User entity ) {

        //region definitions
        final var id       = entity.getId();
        final var name     = ProperName.of( entity.getName() );
        final var birthday = AdultBirthday.of( entity.getBirthday() );
        final var email    = EmailAddress.of( entity.getEmail() );
        final var cpf      = CPF.of( entity.getCpf() );
        //endregion

        return RegistrationDto.builder()
                              .id( id )
                              .name( name ).birthday( birthday )
                              .email( email ).cpf( cpf )
                              .build();
    }
}

