package canelhas.cars.common.languaj.noun;

import java.util.function.Function;

@FunctionalInterface
public interface Conditional< K, V > extends Function< K, V > {
    default V on( K t ) {
        return apply( t );
    }

}
