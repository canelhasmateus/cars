package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CPFTest {

    @Test
    void givenNullThenThrows( ) {

        assertThrows( DomainException.class, ( ) -> CPF.of( null ) );
    }

    @Test
    void givenTooSmallThenThrows( ) {

        assertThrows( DomainException.class, ( ) -> CPF.of( "1111" ) );
    }

    @Test
    void givenTooBigThenThrows( ) {

        assertThrows( DomainException.class, ( ) -> CPF.of( "11111111111111111111111111" ) );

    }

    @Test
    void givenInvalidThenThrows( ) {

        assertThrows( DomainException.class, ( ) -> CPF.of( "22222222222" ) );

    }

    @Test
    void givenValidThenParses( ) {
        String cpf = "07068850669";
        assertEquals( cpf, CPF.of( cpf ).value() );
    }


}