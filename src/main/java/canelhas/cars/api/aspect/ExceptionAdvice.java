package canelhas.cars.api.aspect;


import canelhas.cars.common.exception.CustomException;
import canelhas.cars.common.languaj.noun.Chain;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;

import static canelhas.cars.common.languaj.Adjectives.hopefully;
import static canelhas.cars.common.languaj.Adverbs.lazily;

@ControllerAdvice
public class ExceptionAdvice {

    public static final String MESSAGE = "message";


    @ExceptionHandler( CustomException.class )
    public static ResponseEntity< ? > handleCars( CustomException exception ) {
        var body = Collections.singletonMap( MESSAGE, exception.getMessage() );
        return new ResponseEntity<>( body, exception.getStatus() );
    }

    //region for exceptions during request body creation
    @ExceptionHandler( ValueInstantiationException.class )
    public ResponseEntity< ? > handle( ValueInstantiationException exception ) {
        return handleConditionally( exception );
    }

    @ExceptionHandler( JsonParseException.class )
    public ResponseEntity< ? > handle( JsonParseException exception ) {
        return handleConditionally( exception );
    }
    //endregion

    //region help
    public ResponseEntity< ? > handleConditionally( Exception exception ) {
        //region definition
        final Function< Exception, ResponseEntity< ? > > handleSpecifically = Chain.of( Exception::getCause )
                                                                                   .andThen( CustomException.class::cast )
                                                                                   .andThen( ExceptionAdvice::handleCars );

        Supplier< ResponseEntity< ? > > treatGenerically = lazily( ExceptionAdvice::handleGeneric, exception );
        //endregion

        return hopefully( handleSpecifically )
                       .apply( exception )
                       .orElseGet( treatGenerically );


    }

    public static ResponseEntity< ? > handleGeneric( Exception exception ) {
        var body = Collections.singletonMap( MESSAGE, exception.getMessage() );
        return new ResponseEntity<>( body, HttpStatus.INTERNAL_SERVER_ERROR );
    }
    //endregion


}
