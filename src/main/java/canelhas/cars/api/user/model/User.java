package canelhas.cars.api.user.model;

import canelhas.cars.common.exception.NotFoundException;
import canelhas.cars.common.type.CPF;
import canelhas.cars.common.type.EmailAddress;
import canelhas.cars.common.type.TypedId;
import canelhas.cars.schema.DatabaseTables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.function.Supplier;

import static canelhas.cars.common.functional.Adjectives.lazily;
import static canelhas.cars.schema.DatabaseColumns.*;

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
                       "Não foi encontrado usuário com o id " + userId );
    }

    //endregion

    //region
    public TypedId< User > typedId( ) {
        return castId( getId() );
    }

    public static TypedId< User > castId( Integer id ) {
        return TypedId.of( id );
    }

    public static User of( TypedId< User > userId ) {
        return User.builder().id( userId.value() ).build();
    }

    //endregion
}
