package canelhas.cars.common.functional;

import java.util.function.Function;

public class Chain {

    //region monorepo
    private Chain( ) {}
    //endregion

    /**
     * enables use of .andThen method chaining over functions ::references, while maintaining syntatical style.
     **/
    public static < K, V > Function< K, V > of( Function< K, V > reference ) {
        return reference;
    }
}
