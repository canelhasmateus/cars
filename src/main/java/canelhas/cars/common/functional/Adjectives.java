package canelhas.cars.common.functional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Adjectives {

    //region monorepo
    private Adjectives( ) {}
    //endregion

    //region maybe
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

    public static < K, V > Function< Optional< K >, Optional< V > > possibly( Function< K, V > action ) {

        return ( Optional< K > k ) -> k.map( action );
    }

    public static < K, V > Function< Optional< K >, V > certainly( Function< K, V > action ) {

        return ( Optional< K > k ) -> k.map( action ).orElse( null );
    }
    //endregion

    //region collection
    public static < K, V > Function< Collection< K >, List< V > > collectively( Function< K, V > action ) {
        // TODO: 18/05/2021 let parameter choose destination collection.
        return ( Collection< K > collection ) -> collection.stream()
                                                           .map( action )
                                                           .collect( Collectors.toList() );

    }

    @SafeVarargs
    public static < K > Consumer< K > applicativelly( Consumer< K >... actions ) {
        return ( K k ) -> {
            for ( Consumer< K > consumer : actions ) {
                consumer.accept( k );
            }
        };
    }
    //endregion

    //region conditionally
    public static < K > Conditional< Boolean, K > conditionally( Supplier< K > trueAction, Supplier< K > falseAction ) {

        // TODO: 18/05/2021 create dedicated functional interface
        return isTrue -> {
            if ( isTrue ) {
                return trueAction.get();
            }
            return falseAction.get();
        };
    }

    public static < K > Conditional< Boolean, K > conditionally( Supplier< K > trueAction ) {

        return conditionally( trueAction,
                              lazily( null ) );
    }


    //endregion

    //region partially
    public static < K, U, V > Function< K, V > partially( BiFunction< K, U, V > action, U element ) {
        return ( K k ) -> action.apply( k, element );
    }

    public static < K, V > Supplier< V > partially( Function< K, V > action, K element ) {
        return ( ) -> action.apply( element );
    }

    public static < K, V > Supplier< V > lazily( Function< K, V > action, K element ) {
        return partially( action, element );
    }

    private static < K > Supplier< K > lazily( K k ) {
        return ( ) -> k;
    }
    //endregion

    @SafeVarargs public static < K, V > Function< K, V > fluently( Function< K, V > action, Consumer< V >... sideEffects ) {

        return ( K k ) -> {
            V result = action.apply( k );
            applicativelly( sideEffects )
                    .accept( result );
            return result;
        };

    }


}
