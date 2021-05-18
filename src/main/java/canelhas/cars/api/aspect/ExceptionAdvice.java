package canelhas.cars.api.aspect;


import canelhas.cars.common.exception.CarsException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.function.Supplier;

import static canelhas.cars.common.functional.Adjectives.conditionally;

@ControllerAdvice
public class ExceptionAdvice {

    public static final String MESSAGE = "message";

    @ExceptionHandler( CarsException.class )
    public ResponseEntity< ? > handle( CarsException exception ) {

        var body = Collections.singletonMap( MESSAGE, exception.getMessage() );
        return new ResponseEntity<>( body, exception.getStatus() );
    }

    @ExceptionHandler( JsonParseException.class )
    public ResponseEntity< ? > handle( JsonParseException exception ) {

        //region definition
        Supplier< ResponseEntity< ? > > handleCars = ( ) -> handle( ( CarsException ) exception.getCause() );

        Supplier< ResponseEntity< ? > > orElseGenericException = ( ) -> {
            var body = Collections.singletonMap( MESSAGE, exception.getMessage() );
            return new ResponseEntity<>( body, HttpStatus.UNPROCESSABLE_ENTITY );
        };

        //endregion

        return conditionally( handleCars, orElseGenericException )
                       .apply( exception.getCause() instanceof CarsException );


    }

    @ExceptionHandler( ValueInstantiationException.class )
    public ResponseEntity< ? > handle( ValueInstantiationException exception ) {

        //region definitions
        Supplier< ResponseEntity< ? > > handleCars = ( ) -> handle( ( CarsException ) exception.getCause() );

        Supplier< ResponseEntity< ? > > orElseGenericException = ( ) -> {
            var body = Collections.singletonMap( MESSAGE, exception.getMessage() );
            return new ResponseEntity<>( body, HttpStatus.UNPROCESSABLE_ENTITY );
        };
        //endregion

        return conditionally( handleCars, orElseGenericException )
                       .apply( exception.getCause() instanceof CarsException );

    }


}
