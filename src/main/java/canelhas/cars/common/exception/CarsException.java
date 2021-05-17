package canelhas.cars.common.exception;

import org.springframework.http.HttpStatus;


public abstract class CarsException extends RuntimeException {

    private final HttpStatus status;

    public CarsException( String message ) {
        super( message );
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }


    public CarsException( String message, HttpStatus httpStatus ) {
        super( message );
        this.status = httpStatus;
    }

    public abstract HttpStatus getStatus( );
}
