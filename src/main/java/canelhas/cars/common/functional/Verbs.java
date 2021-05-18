package canelhas.cars.common.functional;

import canelhas.cars.api.auth.domain.AccessException;

import java.util.function.Function;
import java.util.function.Supplier;

public class Verbs {
    public static Supplier< Exception > raise( Supplier< AccessException > exception ) {
        return ( ) -> { throw exception.get();};
    }

    public static < K, V > V combine( Function< K, V > action, K element ) {
        return action.apply( element );
    }
}
