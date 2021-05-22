package canelhas.cars.api.vehicles.crs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Insertion< T > {

    private final T entity;

    public static < T > Insertion< T > of( T value ) {
        return new Insertion<>( value );
    }

}
