package canelhas.cars.common.languaj;

import canelhas.cars.common.languaj.noun.Conditional;
import canelhas.cars.common.languaj.noun.Functional;
import canelhas.cars.common.languaj.noun.FunctionalSupplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static canelhas.cars.common.languaj.Adverbs.logically;

public class Adjectives {

    //region optionals
    public static < T > Conditional< T > conditionally( T element ) {
        return ( Boolean b ) -> {
            if ( Boolean.TRUE.equals( b ) ) {
                return Optional.ofNullable( element );
            }
            else {
                return Optional.empty();
            }
        };
    }

    public static < K, V > Functional< K, Optional< V > > hopefully( Function< K, V > action ) {
        return ( K k ) -> {
            try {
                return Optional.ofNullable( action.apply( k ) );
            }
            catch ( Exception e ) {
                return Optional.empty();
            }
        };
    }
    //endregion

    //region functions
    public static < K, U, V > Functional< U, V > partially( BiFunction< K, U, V > action, K element ) {
        return ( U u ) -> action.apply( element, u );
    }

    public static < K, U, V > Functional< K, V > partially( U element, BiFunction< K, U, V > action ) {
        return ( K k ) -> action.apply( k, element );
    }

    public static < K, V > FunctionalSupplier< V > lazily( Function< K, V > action, K element ) {
        return ( Void v ) -> action.apply( element );
    }
    //endregion

    //region collections
    public static < K, V > Functional< List< K >, List< V > > collectively( Function< K, V > action ) {
        return ( List< K > iterable ) -> {

            final var result = new ArrayList< V >( iterable.size() );

            for ( K element : iterable ) {
                result.add( action.apply( element ) );
            }

            return result;
        };

    }

    public static < K > Functional< Collection< K >, Collection< K > > narrowingly( Function< K, Boolean > chooser ) {
        return ( Collection< K > collection ) -> collection.stream()
                                                           .filter( logically( chooser ) )
                                                           .collect( Collectors.toList() );
    }

    public static < K, V > Functional< List< K >, List< V > > concurrently( Function< K, V > action ) {
        return ( List< K > collection ) -> collection.stream()
                                                     .parallel()
                                                     .map( action )
                                                     .collect( Collectors.toList() );

    }
    //endregion

    //region monorepo
    private Adjectives( ) {}
    //endregion
}
