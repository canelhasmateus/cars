package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailAddressTest {

    @Test
    public void givenNullThenThrows( ) {

        assertThrows( DomainException.class, ( ) -> EmailAddress.of( null ) );
    }

    @Test
    public void givenInvalidThenThrows( ) {

        assertThrows( DomainException.class, ( ) -> EmailAddress.of( "mateus.canelhas@@" ) );
    }

    @Test
    public void givenValidThenParses( ) {
        String email = "mateus.canelhas@gmail.com";

        String value = EmailAddress.of( email ).value();
        assertEquals( email, value );
    }


}