package canelhas.cars.common.languaj.noun;

import java.util.function.Predicate;

@FunctionalInterface
public interface FunctionalPredicate< K > extends Functional< K, Boolean >, Predicate< K > {

    default boolean test( K k ) {
        return apply( k );
    }
}
