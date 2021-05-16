package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProperNameTest {

    @Test
    void givenNullThenThrows( ) {
        assertThrows( DomainException.class, ( ) -> ProperName.of( null ) );
    }

    @Test
    void givenInvalidCharactersThenThrows( ) {
        assertThrows( DomainException.class, ( ) -> ProperName.of( "123123" ) );
    }

    @Test
    void givenValidThenParsesWithCapitalization( ) {

        var name = "mateus canêlhas";
        assertEquals( "Mateus Canêlhas", ProperName.of( name ).value() );
    }

}