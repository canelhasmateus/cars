package canelhas.cars.common.functional;

import canelhas.cars.common.exception.CarsException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Validation {

    private final HttpStatus        httpStatus;
    private       List< Exception > errors;

    public Validation( HttpStatus responseStatus ) {
        httpStatus = responseStatus;
    }

    public < K, V > V map( K element, Function< K, V > action ) {

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

            throw new CarsException( message, httpStatus );
        }


    }
}