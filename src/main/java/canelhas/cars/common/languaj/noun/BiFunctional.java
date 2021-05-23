package canelhas.cars.common.languaj.noun;

import java.util.function.BiFunction;

import static canelhas.cars.common.languaj.Adjectives.partially;

@FunctionalInterface
public interface BiFunctional< K, U, V > extends BiFunction< K, U, V > {

    default Functional< K, V > setSecond( U u ) {
        return partially( u, this );
    }

    default Functional< U, V > partialize( K k ) {
        return partially( this, k );
    }
}
