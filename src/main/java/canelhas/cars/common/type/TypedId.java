package canelhas.cars.common.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypedId< T > extends ValueType< Integer > {

    private final Integer value;

    public static < T > TypedId< T > of( Integer id ) {
        return new TypedId<>( id );
    }


    public Integer value( ) {
        return value;
    }

}
