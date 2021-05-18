package canelhas.cars.api.auth.domain;

import canelhas.cars.common.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AccessException extends CustomException {

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
