package canelhas.cars.common.languaj.noun;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Validation {


    private final Function< String, ? extends RuntimeException > messageReceiver;
    private       List< Exception >                              errors;

    public Validation( Function< String, ? extends RuntimeException > messageReceiver ) {
        this.messageReceiver = messageReceiver;
    }

    public < K, V > V check( K element, Function< K, V > action ) {

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
                                .sorted( Comparator.comparing( String::length ) )
                                .collect( Collectors.joining( " \n " ) );
            throw messageReceiver.apply( message );
        }


    }
}