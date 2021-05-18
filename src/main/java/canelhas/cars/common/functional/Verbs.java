package canelhas.cars.common.functional;

import java.util.function.Function;
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


    public static < K, V > V combine( Function< K, V > action, K element ) {
        return action.apply( element );
    }


}
