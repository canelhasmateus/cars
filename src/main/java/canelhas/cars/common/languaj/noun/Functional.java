package canelhas.cars.common.languaj.noun;

import java.util.function.Function;

public interface Functional< K, V > extends Function< K, V > {

    default V basedOn( K k ) {
        return apply( k );
    }

    default V on( K k ) {
        return apply( k );
    }

    default V in( K k ) {
        return apply( k );
    }


}
