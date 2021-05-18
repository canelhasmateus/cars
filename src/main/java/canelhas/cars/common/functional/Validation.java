package canelhas.cars.common.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Validation {


    private final Function< String, ? extends RuntimeException > thrower;
    private       List< Exception >                              errors;

    public Validation( Function< String, ? extends RuntimeException > thrower ) {
        this.thrower = thrower;
    }

    public < K, V > V assemble( K element, Function< K, V > action ) {

        try {
            return action.apply( element );
        }
        catch ( Exception e ) {
            if ( errors == null ) {
                errors = new ArrayList<>();
            }
            errors.add( e );
            return null;
        }
    }


    public void verify( ) {

        if ( errors != null && !errors.isEmpty() ) {

            var message = errors.stream()
                                .map( Exception::getMessage )
                                .filter( Objects::nonNull )
                                .collect( Collectors.joining( "\n" ) );
            throw thrower.apply( message );
        }


    }
}