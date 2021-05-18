package canelhas.cars.common.functional;

@FunctionalInterface
public interface Conditional< K, V > {
    V on( K t );
}
