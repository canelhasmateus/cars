package canelhas.cars.common.languaj;

import canelhas.cars.common.languaj.noun.Functional;

import java.util.function.Supplier;

public class Verbs {

    public static Void raise( Supplier< ? extends RuntimeException > exception ) {
        throw exception.get();
    }

    public static < T > Functional< T, T > nothing( T t ) {
        return ( T v ) -> t;
    }

    //region monorepo
    private Verbs( ) {}

    //endregion

}
