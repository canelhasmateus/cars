package canelhas.cars.common.utils;

import java.util.Optional;
import java.util.function.Function;

import static canelhas.cars.common.functional.Adjectives.*;

public class TypingHelper {
    //region monorepo

    private TypingHelper( ) {}

    //endregion

    public static < T extends Enum< T > > Optional< T > optionalOf( Class< T > enumeration, String name ) {
        //region definitions
        final Function< String, T > convert = partially( Enum::valueOf, enumeration );
        //endregion
        return hopefully( convert ).apply( name );
    }

    public static Optional< String > optionalOf( String input ) {
        return Optional.ofNullable( input )
                       .filter( logicallyNot( ""::equals ) );
    }
}
