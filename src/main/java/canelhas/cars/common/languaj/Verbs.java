package canelhas.cars.common.languaj;

import java.util.function.Supplier;

public class Verbs {

    //region monorepo
    private Verbs( ) {}

    //endregion
    public static Supplier< Exception > raise( Supplier< ? extends RuntimeException > exception ) {
        return ( ) -> {
            throw exception.get();
        };
    }
}
