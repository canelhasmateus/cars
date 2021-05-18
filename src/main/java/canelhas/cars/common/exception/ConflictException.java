package canelhas.cars.common.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends CustomException {
    private final HttpStatus status;

    public ConflictException( String message ) {
        super( message );
        this.status = HttpStatus.CONFLICT;
    }

    public ConflictException( String message, HttpStatus httpStatus ) {
        super( message, httpStatus );
        this.status = httpStatus;
    }

    @Override public HttpStatus getStatus( ) {
        return status;
    }
}
