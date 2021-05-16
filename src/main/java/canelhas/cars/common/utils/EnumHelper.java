package canelhas.cars.common.utils;

import java.util.Optional;

public class EnumHelper {


    public static < T extends Enum< T > > Optional< T > fromName( Class< T > enumeration, String name ) {

        try {

            return Optional.of( Enum.valueOf( enumeration, name ) );

        }

        catch ( Exception e ) {

            return Optional.empty();
        }
    }

}
