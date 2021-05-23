package canelhas.cars.common.languaj.noun;

import java.util.function.UnaryOperator;

@FunctionalInterface
public interface FunctionalOperator< K > extends Functional< K, K >, UnaryOperator< K > {
}
