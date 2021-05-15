package canelhas.cars.common.utils;

import java.util.Date;

public class DateHelper {
    public static int yearDifference( Date start, Date finish ) {

        long diff = start.getTime() - finish.getTime();

        return ( int ) ( diff / 1000 / 60 / 60 / 24 / 365 );
    }
}
