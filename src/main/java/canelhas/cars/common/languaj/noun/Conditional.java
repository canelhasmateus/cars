package canelhas.cars.common.languaj.noun;

@FunctionalInterface
public interface Conditional< K, V > {
    V on( K t );
}
