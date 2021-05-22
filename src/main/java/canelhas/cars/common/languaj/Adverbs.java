package canelhas.cars.common.languaj;

import canelhas.cars.common.languaj.noun.Conditional;

import java.util.function.*;

public class Adverbs {

    public static < K, V > Conditional< Boolean, Function< K, V > > conditionally( Function< K, V > action ) {
        return isTrue -> {
            if ( Boolean.TRUE.equals( isTrue ) ) {
                return action;
            }
            return k -> null;
        };
    }

    public static < K > Conditional< Boolean, K > conditionally( Supplier< K > trueAction, Supplier< K > falseAction ) {
        return isTrue -> {
            if ( Boolean.TRUE.equals( isTrue ) ) {
                return trueAction.get();
            }
            return falseAction.get();
        };
    }

    public static < K > Conditional< Boolean, UnaryOperator< K > > conditionally( Consumer< K > action ) {
        return isTrue -> {
            if ( Boolean.TRUE.equals( isTrue ) ) {
                return ( K k ) -> {
                    action.accept( k );
                    return k;
                };
            }
            return k -> k;
        };
    }

    public static < K > Conditional< Boolean, K > conditionally( Supplier< K > sideEffect ) {
        return conditionally( sideEffect, ( ) -> null );
    }

    public static < T > Function< T, Void > functionally( Consumer< T > consumer ) {
        return ( T t ) -> {
            consumer.accept( t );
            return null;
        };
    }

    public static < K, V > Supplier< V > lazily( Function< K, V > action, K element ) {
        return ( ) -> action.apply( element );
    }

    public static < K > Predicate< K > logically( Function< K, Boolean > predicate ) {
        return predicate::apply;
    }

    public static < K > Predicate< K > logicallyNot( Function< K, Boolean > predicate ) {
        return Predicate.not( logically( predicate ) );
    }
}
