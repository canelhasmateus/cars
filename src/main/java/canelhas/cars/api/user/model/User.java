package canelhas.cars.api.user.model;

import canelhas.cars.api.util.ExceptionMessages;
import canelhas.cars.common.exception.ConflictException;
import canelhas.cars.common.exception.NotFoundException;
import canelhas.cars.common.type.*;
import canelhas.cars.schema.DatabaseTables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.CPF_IS_USED;
import static canelhas.cars.api.util.ExceptionMessages.EMAIL_IS_USED;
import static canelhas.cars.common.languaj.Adjectives.lazily;
import static canelhas.cars.schema.DatabaseColumns.*;
import static java.lang.String.format;

@Builder( toBuilder = true )
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = DatabaseTables.USERS )
public class User {

    //region fields
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = USER_ID )
    private Integer id;

    @Column( name = USER_NAME )
    private String name;

    @Column( name = USER_CPF, unique = true )

    private String cpf;

    @Column( name = USER_EMAIL, unique = true )
    private String email;

    @Column( name = USER_BIRTHDAY )
    private Date birthday;


    //endregion

    //region exceptions
    public static Supplier< NotFoundException > notFound( CPF cpf, EmailAddress email ) {
        return lazily( NotFoundException::new,
                       "Não foi encontrado usuário com email " + email + " e cpf " + cpf + "." );
    }

    public static Supplier< NotFoundException > notFound( TypedId< User > userId ) {
        return lazily( NotFoundException::new,
                       format( ExceptionMessages.USER_NOT_FOUND_WITH_ID, userId ) );
    }

    public static Supplier< ConflictException > alreadyExistsWith( CPF cpf ) {
        return lazily( ConflictException::new,
                       format( CPF_IS_USED, cpf.value() ) );
    }

    public static Supplier< ConflictException > alreadyExistsWith( EmailAddress email ) {
        return lazily( ConflictException::new,
                       format( EMAIL_IS_USED, email.value() ) );
    }
    //endregion

    //region
    public TypedId< User > typedId( ) {
        return TypedId.of( getId() );
    }

    public ProperName typedName( ) {
        return ProperName.of( this.getName() );
    }

    public AdultBirthday typedBirthday( ) {
        return AdultBirthday.of( this.getBirthday() );
    }

    public EmailAddress typedEmail( ) {
        return EmailAddress.of( this.getEmail() );
    }

    public CPF typedCpf( ) {
        return CPF.of( this.getCpf() );
    }


    public static User of( TypedId< User > userId ) {
        return User.builder()
                   .id( userId.value() )
                   .build();
    }
    //endregion
}
