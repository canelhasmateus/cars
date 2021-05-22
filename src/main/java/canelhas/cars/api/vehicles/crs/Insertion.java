package canelhas.cars.api.vehicles.crs;

import canelhas.cars.common.type.ValueType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Insertion< T > extends ValueType< T > {

    private final T value;

    public static < T > Insertion< T > of( T value ) {
        return new Insertion<>( value );
    }

    @Override public T value( ) {
        return value;
    }
}
