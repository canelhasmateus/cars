package canelhas.cars.common.utils;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    //region monorepo

    private DateHelper( ) {}

    //endregion
    public static int findAge( Date start, Date finish ) {

        long diff = start.getTime() - finish.getTime();

        return ( int ) ( diff / 1000 / 60 / 60 / 24 / 365 );
    }

    public static int getCurrentWeekDay( ) {
        var instance = Calendar.getInstance();
        instance.setTime( new Date() );
        return instance.get( Calendar.DAY_OF_WEEK );
    }
}
