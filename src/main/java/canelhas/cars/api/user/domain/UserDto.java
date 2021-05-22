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

    @JsonProperty( ID )
    private final TypedId< User > id;

    @JsonProperty( NAME )
    private final ProperName name;

    @JsonProperty( EMAIL )
    private final EmailAddress email;

    @JsonProperty( CPF_KEY )
    private final CPF cpf;

    @JsonProperty( BIRTHDAY )
    private final AdultBirthday birthday;

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static UserDto of( @JsonProperty( NAME ) String name,
                              @JsonProperty( EMAIL ) String email,
                              @JsonProperty( CPF_KEY ) String cpf,
                              @JsonProperty( BIRTHDAY ) @JsonFormat( pattern = "yyyy-MM-dd", timezone = "Brazil/East" ) Date birthday ) {

        //region definitions
        final var validation   = new Validation( DomainException::new );
        final var emailAddress = validation.check( email, EmailAddress::of );
        final var cpfValue     = validation.check( cpf, CPF::of );
        final var properName   = validation.check( name, ProperName::of );
        final var userBirthday = validation.check( birthday, AdultBirthday::of );
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

        return UserDto.builder()
                      .id( entity.typedId() )
                      .name( entity.typedName() )
                      .birthday( entity.typedBirthday() )
                      .email( entity.typedEmail() )
                      .cpf( entity.typedCpf() )
                      .build();
    }


    //region keys
    public static final String NAME     = "name";
    public static final String EMAIL    = "email";
    public static final String CPF_KEY  = "cpf";
    public static final String BIRTHDAY = "birthday";
    public static final String ID       = "id";
    //endregion
}

