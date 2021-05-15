package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AdultBirthdayTest {

    @Test
    public void givenNullThenThrows( ) {

        assertThrows( DomainException.class, ( ) -> AdultBirthday.of( null ) );

    }

    @Test
    public void givenFutureDateThenThrows( ) {

        var calendar = Calendar.getInstance();
        calendar.setTime( new Date() );
        calendar.add( Calendar.YEAR, 1 );

        assertThrows( DomainException.class, ( ) -> AdultBirthday.of( calendar.getTime() ) );

    }


    @Test
    public void givenRecentPastDateThenThrows( ) {

        var calendar = Calendar.getInstance();
        calendar.setTime( new Date() );
        calendar.add( Calendar.YEAR, -10 );

        assertThrows( DomainException.class, ( ) -> AdultBirthday.of( calendar.getTime() ) );
    }

    @Test
    public void givenValidDateThenParses( ) {

        var birthday = new GregorianCalendar( 1997, Calendar.MARCH, 4 ).getTime();
        assertEquals( birthday, AdultBirthday.of( birthday ).value() );

    }
}