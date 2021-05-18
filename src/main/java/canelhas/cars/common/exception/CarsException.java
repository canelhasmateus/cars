package canelhas.cars.common.exception;

import org.springframework.http.HttpStatus;


public abstract class CarsException extends RuntimeException {

    protected CarsException( String message ) {
        super( message );
    }


    protected CarsException( String message, HttpStatus httpStatus ) {
        super( message );
    }

    public abstract HttpStatus getStatus( );
}
