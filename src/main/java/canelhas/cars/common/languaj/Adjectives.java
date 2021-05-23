package canelhas.cars.common.languaj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static canelhas.cars.common.languaj.Adverbs.logically;

public class Adjectives {

    public static < K, V > Function< K, Optional< V > > hopefully( Function< K, V > action ) {
        return ( K k ) -> {
            try {
                return Optional.ofNullable( action.apply( k ) );
            }
            catch ( Exception e ) {

                return Optional.empty();
            }
        };
    }

    public static < K, V > Function< Collection< K >, List< V > > collectively( Function< K, V > action ) {
        return ( Collection< K > iterable ) -> {

            final var result = new ArrayList< V >( iterable.size() );

            for ( K element : iterable ) {
                result.add( action.apply( element ) );
            }

            return result;
        };

    }

    public static < K, V > Function< Collection< K >, List< V > > concurrently( Function< K, V > action ) {
        return ( Collection< K > collection ) -> collection.stream()
                                                           .parallel()
                                                           .map( action )
                                                           .collect( Collectors.toList() );

    }

    public static < K > Function< Collection< K >, Collection< K > > narrowingly( Function< K, Boolean > chooser ) {
        return ( Collection< K > collection ) -> collection.stream()
                                                           .filter( logically( chooser ) )
                                                           .collect( Collectors.toList() );
    }

    public static < K, U, V > Function< U, V > partially( BiFunction< K, U, V > action, K element ) {
        return ( U u ) -> action.apply( element, u );
    }

    public static < K, U, V > Function< K, V > partially( U element, BiFunction< K, U, V > action ) {
        return ( K k ) -> action.apply( k, element );
    }
    //endregion

    //region monorepo
    private Adjectives( ) {}
    //endregion
}
