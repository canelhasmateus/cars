package canelhas.cars.common.type;

public class TypedId< T > {

    //    WHY IS THERE A ERROR WHEN EXTENDING VALUETYPE?
    private final Integer value;

    public TypedId( Class< T > clazz, Integer id ) {
        this.value = id;
    }

    public static < T > TypedId< T > of( Class< T > clazz, Integer id ) {
        return new TypedId<>( clazz, id );
    }


    public Integer value( ) {
        return value;
    }

}
