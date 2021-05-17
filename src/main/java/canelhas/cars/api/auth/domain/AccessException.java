package canelhas.cars.api.auth.domain;

import canelhas.cars.common.exception.CarsException;
import org.springframework.http.HttpStatus;

public class AccessException extends CarsException {

    private final HttpStatus status;

    public AccessException( String message ) {
        super( message );
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public AccessException( String message, HttpStatus httpStatus ) {
        super( message );
        this.status = httpStatus;
    }

    @Override public HttpStatus getStatus( ) {
        return status;
    }
}
