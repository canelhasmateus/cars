package canelhas.cars.api.user.domain;


import canelhas.cars.api.user.model.User;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.functional.Validation;
import canelhas.cars.common.type.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.Date;
import java.util.Optional;


@Builder( toBuilder = true )
@Value
public class UserDto {

    @JsonProperty( "id" )
    private final TypedId< User > id;

    @JsonProperty( "name" )
    private final ProperName name;

    @JsonProperty( "email" )
    private final EmailAddress email;

    @JsonProperty( "cpf" )
    private final CPF cpf;

    @JsonProperty( "birthday" )
    private final AdultBirthday birthday;

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static UserDto of( @JsonProperty( "name" ) String name,
                              @JsonProperty( "email" ) String email,
                              @JsonProperty( "cpf" ) String cpf,
                              @JsonProperty( "birthday" ) @JsonFormat( pattern = "yyyy-MM-dd", timezone = "Brazil/East" ) Date birthday ) {

        //region definitions
        var       validation   = new Validation( DomainException::new );
        final var emailAddress = validation.assemble( email, EmailAddress::of );
        final var cpfValue     = validation.assemble( cpf, CPF::of );
        final var properName   = validation.assemble( name, ProperName::of );
        final var userBirthday = validation.assemble( birthday, AdultBirthday::of );
        validation.verify();
        //endregion

        return UserDto.builder()
                      .id( null )
                      .email( emailAddress )
                      .cpf( cpfValue )
                      .name( properName )
                      .birthday( userBirthday )
                      .build();
    }


    public static User toEntity( UserDto request ) {

        //region definitions
        final var id = Optional.ofNullable( request.getId() )
                               .map( TypedId::value )
                               .orElse( null );
        final var cpf      = request.getCpf().value();
        final var birthday = request.getBirthday().value();
        final var email    = request.getEmail().value();
        final var name     = request.getName().value();
        //endregion

        return User.builder()
                   .id( id )
                   .name( name )
                   .birthday( birthday )
                   .email( email )
                   .cpf( cpf )
                   .build();


    }


    public static UserDto fromEntity( User entity ) {

        //region definitions
        final var id       = TypedId.of( User.class, entity.getId() );
        final var name     = ProperName.of( entity.getName() );
        final var birthday = AdultBirthday.of( entity.getBirthday() );
        final var email    = EmailAddress.of( entity.getEmail() );
        final var cpf      = CPF.of( entity.getCpf() );
        //endregion

        return UserDto.builder()
                      .id( id )
                      .name( name )
                      .birthday( birthday )
                      .email( email )
                      .cpf( cpf )
                      .build();
    }
}

