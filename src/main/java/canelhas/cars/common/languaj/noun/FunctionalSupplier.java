package canelhas.cars.common.languaj.noun;

import java.util.function.Supplier;

@FunctionalInterface
public interface FunctionalSupplier< V > extends Functional< Void, V >, Supplier< V > {

    default V get( ) {
        return apply( null );
    }
}
