package canelhas.cars.api.util;

import canelhas.cars.api.vehicles.domain.ModelYear;
import canelhas.cars.api.vehicles.model.VehicleModel;
import canelhas.cars.common.functional.Chain;

import java.util.*;

import static canelhas.cars.common.functional.Adjectives.partially;
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

    public static Boolean rotatedOut( int currentWeekday, ModelYear year ) {

        var lastDigit = ModelYear.asInteger( year ) % 10;

        return currentWeekday == rotationDay.getOrDefault( lastDigit, -1 );
    }

    public static Boolean isRotatedOut( VehicleModel vehicleModel ) {

        //region current weekDay
        var instance = Calendar.getInstance();
        instance.setTime( new Date() );
        final var currentWeekDay = instance.get( Calendar.DAY_OF_WEEK );

        final var isRotatedToday = partially( RotationHelper::rotatedOut, currentWeekDay );
        final var checkRotation  = Chain.of( ModelYear::of ).andThen( isRotatedToday );
        //endregion

        return Optional.ofNullable( vehicleModel.getYear() )
                       .map( checkRotation )
                       .orElse( null );

    }

    //region monorepo

    private RotationHelper( ) {}

    //endregion

}
