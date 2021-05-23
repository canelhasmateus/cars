package canelhas.cars.common.languaj.noun;

import java.util.function.Consumer;

@FunctionalInterface
public interface FunctionalConsumer< T > extends Functional< T, Void >, Consumer< T > {
    default void accept( T t ) { apply( t );}
}
