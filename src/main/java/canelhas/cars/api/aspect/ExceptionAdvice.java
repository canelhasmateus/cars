package canelhas.cars.api.aspect;


import canelhas.cars.common.exception.CarsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static canelhas.cars.common.functional.Adjectives.conditionally;
import static canelhas.cars.common.functional.Adjectives.partially;

@ControllerAdvice
public class ExceptionAdvice {

    public static final String MESSAGE = "message";

    @ExceptionHandler( Exception.class )
    public ResponseEntity< ? > handle( Exception exception ) {
        //region definition
        Supplier< ResponseEntity< ? > > handleCars = partially( this::handleCars,
                                                                ( CarsException ) exception.getCause() );

        Supplier< ResponseEntity< ? > > orElseGenericException = partially( this::handleGeneric, exception );

        Predicate< Exception > isCarException = e -> e instanceof CarsException ||
                                                     e.getCause() instanceof CarsException;
        //endregion

        return conditionally( handleCars, orElseGenericException )
                       .on( isCarException.test( exception ) );

    }


    //region help
    public ResponseEntity< ? > handleGeneric( Exception exception ) {
        var body = Collections.singletonMap( MESSAGE, exception.getMessage() );
        return new ResponseEntity<>( body, HttpStatus.INTERNAL_SERVER_ERROR );
    }

    public ResponseEntity< ? > handleCars( CarsException exception ) {
        var body = Collections.singletonMap( MESSAGE, exception.getMessage() );
        return new ResponseEntity<>( body, exception.getStatus() );
    }
    //endregion


}
