package canelhas.cars.common.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TypedId< T > extends ValueType< Integer > {

    private final Integer value;

    public TypedId( Class< T > clazz, Integer id ) {
        this.value = id;
    }

    public static < T > TypedId< T > of( Class< T > clazz, Integer id ) {
        return new TypedId<>( clazz, id );
    }

    public static < T > TypedId< T > of( Integer id ) {
        return new TypedId<>( id );
    }


    public Integer value( ) {
        return value;
    }

}
