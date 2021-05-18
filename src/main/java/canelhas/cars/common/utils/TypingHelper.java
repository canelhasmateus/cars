package canelhas.cars.common.utils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    public static < K, V > Function< Optional< K >, Optional< V > > possibly( Function< K, V > action ) {

        return ( Optional< K > k ) -> k.map( action );
    }

    public static < K, V > Supplier< V > lazily( Function< K, V > action, K element ) {

        return ( ) -> action.apply( element );

    }

    public static < K, V > Function< Collection< K >, List< V > > collectively( Function< K, V > action ) {

        return ( Collection< K > collection ) -> collection.stream()
                                                           .map( action )
                                                           .collect( Collectors.toList() );

    }


}
