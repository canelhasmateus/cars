package canelhas.cars.common.languaj.noun;

import java.util.function.Function;

public interface Functional< K, V > extends Function< K, V > {

    @Override default < V2 > Functional< K, V2 > andThen( Function< ? super V, ? extends V2 > after ) {
        return ( K k ) -> after.apply( this.apply( k ) );
    }

    @Override default < V2 > Functional< V2, V > compose( Function< ? super V2, ? extends K > before ) {
        return before.andThen( this )::apply;
    }

    @SuppressWarnings( "unchecked" )
    default < V2, F extends Function< K, V > > V2 qualify( Function< F, V2 > qualifier ) {
        return qualifier.apply( ( F ) this );
    }


}
