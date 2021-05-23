package canelhas.cars.common.languaj;

import canelhas.cars.common.languaj.noun.Conditional;
import canelhas.cars.common.languaj.noun.Functional;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.function.Supplier;

public class Verbs {


    public static < X extends RuntimeException > Conditional< Void > raise( Supplier< X > supplier ) throws X {
        return ( Boolean condition ) -> {
            if ( Boolean.TRUE.equals( condition ) ) {
                throw supplier.get();
            }
            else {
                return Optional.empty();
            }
        };
    }

    public static < T > Functional< T, T > nothing( T t ) {
        return ( T v ) -> t;
    }

    //region monorepo
    private Verbs( ) {}

    //endregion

}
