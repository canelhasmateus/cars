package canelhas.cars.common.utils;

import java.util.Optional;

public class TypingHelper {

    //region monorepo

    private TypingHelper( ) {}

    //endregion
    public static < T extends Enum< T > > Optional< T > maybe( Class< T > enumeration, String name ) {

        try {

            return Optional.of( Enum.valueOf( enumeration, name ) );

        }

        catch ( Exception e ) {

            return Optional.empty();
        }
    }

}
