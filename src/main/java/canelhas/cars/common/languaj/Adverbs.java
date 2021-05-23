package canelhas.cars.common.languaj;

import canelhas.cars.common.languaj.noun.FunctionalConsumer;
import canelhas.cars.common.languaj.noun.FunctionalOperator;
import canelhas.cars.common.languaj.noun.FunctionalPredicate;
import canelhas.cars.common.languaj.noun.FunctionalSupplier;

import java.util.function.*;

public class Adverbs {

    //region functionally
    public static < K > FunctionalPredicate< K > functionally( Predicate< K > predicate ) {
        return predicate::test;
    }

    public static < K > FunctionalConsumer< K > functionally( Consumer< K > consumer ) {
        return ( K k ) -> {
            consumer.accept( k );
            return null;
        };
    }

    public static < K > FunctionalOperator< K > functionally( UnaryOperator< K > operator ) {
        return operator::apply;
    }

    public static < V > FunctionalSupplier< V > functionally( Supplier< V > supplier ) {
        return ( Void v ) -> supplier.get();
    }
    //endregion

    public static < K > FunctionalPredicate< K > logically( Function< K, Boolean > predicate ) {
        return predicate::apply;
    }

    public static < K > FunctionalPredicate< K > logicallyNot( Function< K, Boolean > predicate ) {
        return Predicate.not( logically( predicate ) )::test;
    }

    //region monorepo
    private Adverbs( ) {}
    //endregion
}
