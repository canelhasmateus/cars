package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProperNameTest {

    @Test
    public void givenNullThenThrows( ) {
        assertThrows( DomainException.class, ( ) -> ProperName.of( null ) );
    }

    @Test
    public void givenInvalidCharactersThenThrows( ) {
        assertThrows( DomainException.class, ( ) -> ProperName.of( "123123" ) );
    }

    @Test
    public void givenValidThenParsesWithCapitalization( ) {

        var name = "mateus canelhas";
        assertEquals( "Mateus Canelhas", ProperName.of( name ).value() );
    }

}