package canelhas.cars.api.user.mvc;

import canelhas.cars.common.type.ModelYear;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static java.util.Calendar.*;

public class RotationHelper {

    private static Boolean rotatedOut( ModelYear year ) {

        //region current weekDay
        Calendar instance = Calendar.getInstance();
        instance.setTime( new Date() );
        final var currentWeekDay = instance.get( Calendar.DAY_OF_WEEK );
        //endregion

        var lastDigit = ModelYear.asInteger( year ) % 10;

        switch ( currentWeekDay ) {
            case MONDAY:
                return ( lastDigit == 0 || lastDigit == 1 );
            case TUESDAY:
                return ( lastDigit == 2 || lastDigit == 3 );
            case WEDNESDAY:
                return ( lastDigit == 4 || lastDigit == 5 );
            case THURSDAY:
                return ( lastDigit == 6 || lastDigit == 7 );
            case FRIDAY:
                return ( lastDigit == 8 || lastDigit == 9 );
            default:
                return false;
        }

    }

    public static Boolean isRotatedOut( Vehicle vehicle ) {

        return Optional.ofNullable( vehicle.getYear() )
                       .map( ModelYear::of )
                       .map( RotationHelper::rotatedOut )
                       .orElse( null );

    }
}
