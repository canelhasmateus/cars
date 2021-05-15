package canelhas.cars.api.aspect;


import canelhas.cars.common.exception.CarsException;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

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

        //region delegate when cars exception.
        // Would love to use instanceof pattern matching here, but java11
        if ( exception.getCause() != null &&
             exception.getCause() instanceof CarsException ) {
            return handle( ( CarsException ) exception.getCause() );
        }
        //endregion

        var body = Collections.singletonMap( MESSAGE, exception.getMessage() );
        return new ResponseEntity<>( body, HttpStatus.UNPROCESSABLE_ENTITY );
    }


}
