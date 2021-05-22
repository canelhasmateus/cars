package canelhas.cars.api.util;

import canelhas.cars.api.vehicles.domain.ModelYear;
import canelhas.cars.api.vehicles.model.VehicleModel;

import java.util.*;

import static java.util.Calendar.*;

public class RotationHelper {

    private static final Map< Integer, Integer > rotationDay = new HashMap<>();

    static {
        rotationDay.put( 0, MONDAY );
        rotationDay.put( 1, MONDAY );
        rotationDay.put( 2, TUESDAY );
        rotationDay.put( 3, TUESDAY );
        rotationDay.put( 4, WEDNESDAY );
        rotationDay.put( 5, WEDNESDAY );
        rotationDay.put( 6, THURSDAY );
        rotationDay.put( 7, THURSDAY );
        rotationDay.put( 8, FRIDAY );
        rotationDay.put( 9, FRIDAY );
    }
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

        return currentWeekDay == rotationDay.getOrDefault( lastDigit, -1 );
    }

    public static Boolean isRotatedOut( VehicleModel vehicleModel ) {

        return Optional.ofNullable( vehicleModel.getYear() )
                       .map( ModelYear::of )
                       .map( RotationHelper::rotatedOut )
                       .orElse( null );

    }
}
