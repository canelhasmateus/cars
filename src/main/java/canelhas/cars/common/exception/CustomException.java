package canelhas.cars.common.exception;

import org.springframework.http.HttpStatus;


public abstract class CustomException extends RuntimeException {

    protected CustomException( String message ) {
        super( message );
    }


    protected CustomException( String message, HttpStatus httpStatus ) {
        super( message );
    }

    public abstract HttpStatus getStatus( );
}
