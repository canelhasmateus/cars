package canelhas.cars.common.functional;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Transform< T > implements Supplier< T > {

    //region init
    private final T value;

    public Transform( T value ) {
        this.value = value;
    }
    //endregion

    //region creation
    public static < T > Transform< T > of( T value ) {

        return new Transform<>( value );

    }

    public static < T > Transform< T > of( Supplier< T > supplier ) {

        return new Transform<>( supplier.get() );
    }
    //endregion

    //region flux
    public < V > Transform< V > map( Function< ? super T, ? extends V > action ) {
        return Transform.of( action.apply( this.value ) );

    }

    public < V > Transform< V > flatMap( Function< ? super T, Transform< V > > action ) {

        return Transform.of( action.apply( this.value ) );

    }

    public Transform< T > peek( Consumer< ? super T > action ) {
        action.accept( this.value );
        return this;
    }
    //endregion

    @Override
    public T get( ) {
        return this.value;
    }

}
