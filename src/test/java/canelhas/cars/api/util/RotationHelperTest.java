package canelhas.cars.api.util;

import canelhas.cars.api.vehicles.domain.ModelYear;
import org.junit.jupiter.api.Test;

import static java.util.Calendar.MONDAY;
import static java.util.Calendar.THURSDAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RotationHelperTest {


    @Test
    void givenExampleAThenReturnsTrue( ) {
        final var year = ModelYear.of( "2001" );
        assertEquals( true, RotationHelper.rotatedOut( MONDAY, year ) );
    }

    @Test
    void givenExampleBThenReturnsFalse( ) {
        final var year = ModelYear.of( "2021" );
        assertEquals( false, RotationHelper.rotatedOut( THURSDAY, year ) );
    }

}