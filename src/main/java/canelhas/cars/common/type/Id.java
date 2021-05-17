package canelhas.cars.common.type;

public class Id< T > {

    //    WHY IS THERE A ERROR WHEN EXTENDING VALUETYPE?
    private final Integer value;

    public Id( Class< T > clazz, Integer id ) {
        this.value = id;
    }

    public static < T > Id< T > of( Class< T > clazz, Integer id ) {
        return new Id<>( clazz, id );
    }


    public Integer value( ) {
        return value;
    }

}
