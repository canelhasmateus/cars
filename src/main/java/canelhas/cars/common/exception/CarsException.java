package canelhas.cars.common.exception;

import org.springframework.http.HttpStatus;


public abstract class CarsException extends RuntimeException {

    protected CarsException( String message ) {

    }


    protected CarsException( String message, HttpStatus httpStatus ) {
    }

    public abstract HttpStatus getStatus( );
}
