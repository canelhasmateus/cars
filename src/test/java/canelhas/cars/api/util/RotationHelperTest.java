package canelhas.cars.api.util;

import canelhas.cars.api.vehicles.type.ModelYear;
import org.junit.jupiter.api.Test;

import static java.util.Calendar.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotationHelperTest {


    @Test
    void givenExampleAThenReturnsTrue( ) {
        final var year = ModelYear.of( "2001" );
        assertTrue( RotationHelper.rotatedOut( MONDAY, year ) );
    }

    @Test
    void givenExampleBThenReturnsFalse( ) {
        final var year = ModelYear.of( "2021" );
        assertFalse( RotationHelper.rotatedOut( THURSDAY, year ) );
    }

    @Test
    void givenExampleBThenReturnsF( ) {
        var year = ModelYear.of( "2021" );
        assertTrue( RotationHelper.rotatedOut( MONDAY, year ) );
        year = ModelYear.of( "2021" );
        assertTrue( RotationHelper.rotatedOut( MONDAY, year ) );
    }

    @Test
    void givenExampleBThenReturns( ) {
        var year = ModelYear.of( "2022" );
        assertTrue( RotationHelper.rotatedOut( TUESDAY, year ) );
        year = ModelYear.of( "2023" );
        assertTrue( RotationHelper.rotatedOut( TUESDAY, year ) );
    }

    @Test
    void givenExampleBThenReturn( ) {
        var year = ModelYear.of( "2024" );
        assertTrue( RotationHelper.rotatedOut( WEDNESDAY, year ) );
        year = ModelYear.of( "2025" );
        assertTrue( RotationHelper.rotatedOut( WEDNESDAY, year ) );

    }


    @Test
    void givenExampleBThenRetur( ) {
        var year = ModelYear.of( "2026" );
        assertTrue( RotationHelper.rotatedOut( THURSDAY, year ) );
        year = ModelYear.of( "2027" );
        assertTrue( RotationHelper.rotatedOut( THURSDAY, year ) );
    }

    @Test
    void givenExampleBTheRetur( ) {
        var year = ModelYear.of( "2028" );
        assertTrue( RotationHelper.rotatedOut( FRIDAY, year ) );
        year = ModelYear.of( "2029" );
        assertTrue( RotationHelper.rotatedOut( FRIDAY, year ) );
    }

}