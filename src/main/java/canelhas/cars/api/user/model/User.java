package canelhas.cars.api.user.model;

import canelhas.cars.schema.DatabaseTables;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static canelhas.cars.schema.DatabaseColumns.*;

@Builder( toBuilder = true )
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = DatabaseTables.USERS)
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
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

}
