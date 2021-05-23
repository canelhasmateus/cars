package canelhas.cars.common.languaj.noun;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Chain {

    //region monorepo
    private Chain( ) {}
    //endregion

    /**
     * enables use of method chaining over functions ::references, while maintaining syntatical style.
     **/
    public static < K, V > Functional< K, V > of( Function< K, V > reference ) {
        return reference::apply;
    }

    public static < K, U, V > BiFunctional< K, U, V > of( BiFunction< K, U, V > reference ) {
        return reference::apply;
    }


}
