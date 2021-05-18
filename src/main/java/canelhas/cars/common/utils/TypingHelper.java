package canelhas.cars.common.utils;

import java.util.Optional;

import static canelhas.cars.common.functional.Adjectives.logicallyNot;

public class TypingHelper {
    //region monorepo

    private TypingHelper(){}
    //endregion
    //endregion
    public static < T extends Enum< T > > Optional< T > maybe( Class< T > enumeration, String name ) {

        try {

            return Optional.of( Enum.valueOf( enumeration, name ) );

        }

        catch ( Exception e ) {

            return Optional.empty();
        }
    }

    public static Optional< String > maybe( String input ) {
        return Optional.ofNullable( input )
                       .filter( logicallyNot( ""::equals ) );
    }
}
