package canelhas.cars.api.util;

import canelhas.cars.api.vehicles.domain.ModelYear;
import canelhas.cars.api.vehicles.model.VehicleModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static java.util.Calendar.*;

public class RotationHelper {

    //region monorepo

    private RotationHelper( ) {}

    //endregion
    private static Boolean rotatedOut( ModelYear year ) {

        //region current weekDay
        var instance = Calendar.getInstance();
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

    public static Boolean isRotatedOut( VehicleModel vehicleModel ) {

        return Optional.ofNullable( vehicleModel.getYear() )
                       .map( ModelYear::of )
                       .map( RotationHelper::rotatedOut )
                       .orElse( null );

    }
}
