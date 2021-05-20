package canelhas.cars.common.functional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collectors;

public class Adjectives {

    //region monorepo
    private Adjectives( ) {}
    //endregion

    //region these ones are probably good
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
        // TODO: 18/05/2021 let parameter choose destination collection.
        return ( Collection< K > iterable ) -> {
            final var result = new ArrayList< V >( iterable.size() );
            for ( K element : iterable ) {
                result.add( action.apply( element ) );
            }
            return result;
        };

    }

    public static < K, V > Function< Collection< K >, List< V > > concurrently( Function< K, V > action ) {
        // FIXME: 18/05/2021 how to let parameter choose destination collection.
        return ( Collection< K > collection ) -> collection.stream().parallel()
                                                           .map( action )
                                                           .collect( Collectors.toList() );

    }

    @SafeVarargs public static < K > UnaryOperator< K > effectfully( Consumer< K >... sideEffects ) {
        return ( K k ) -> {
            for ( Consumer< K > consumer : sideEffects ) {
                consumer.accept( k );
            }
            return k;
        };

    }

    @SafeVarargs public static < K > Function< K, K > effectfully( Function< K, ? >... sideEffects ) {
        return ( K k ) -> {
            for ( Function< K, ? > function : sideEffects ) {
                function.apply( k );
            }
            return k;
        };

    }

    public static < K > UnaryOperator< Collection< K > > narrowingly( Function< K, Boolean > chooser ) {
        // FIXME: 18/05/2021 how to let parameter choose destination collection.
        return ( Collection< K > collection ) -> collection.stream()
                                                           .filter( logically( chooser ) )
                                                           .collect( Collectors.toList() );
    }

    public static < K, V > Supplier< V > lazily( Function< K, V > action, K element ) {
        return ( ) -> action.apply( element );
    }

    public static < U, K, V > Function< K, V > partially( BiFunction< U, K, V > action, U element ) {
        return ( K k ) -> action.apply( element, k );
    }

    public static < K, U, V > Function< K, V > partially( U element, BiFunction< K, U, V > action ) {
        return ( K k ) -> action.apply( k, element );
    }

    //endregion

    //region probably remake these.
    public static < K > Conditional< Boolean, K > conditionally( Supplier< K > trueAction, Supplier< K > falseAction ) {
        return isTrue -> {
            if ( Boolean.TRUE.equals( isTrue ) ) {
                return trueAction.get();
            }
            return falseAction.get();
        };
    }

    public static < K, V > Conditional< Boolean, Function< K, V > > conditionally( Function< K, V > action ) {
        return isTrue -> {
            if ( Boolean.TRUE.equals( isTrue ) ) {
                return action;
            }
            return k -> null;
        };
    }

    public static < K > Conditional< Boolean, UnaryOperator< K > > conditionally( Consumer< K > action ) {
        return isTrue -> {
            if ( Boolean.TRUE.equals( isTrue ) ) {
                return effectfully( action );
            }
            return k -> k;
        };
    }

    public static < K > Conditional< Boolean, K > conditionally( Supplier< K > sideEffect ) {

        return conditionally( sideEffect, lazily( null ) );
    }

    private static < K > Supplier< K > lazily( K k ) {
        return ( ) -> k;
    }

    public static < K > Predicate< K > logically( Function< K, Boolean > predicate ) {
        return predicate::apply;
    }

    public static < K > Predicate< K > logicallyNot( Function< K, Boolean > predicate ) {
        return Predicate.not( logically( predicate ) );
    }
    //endregion

}
