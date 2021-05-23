package canelhas.cars.common.languaj.noun;

import java.util.Optional;

public interface Conditional< K > extends Functional< Boolean, Optional< K > > {

    default Optional< K > when( Boolean b ) {
        return apply( b );
    }
}
