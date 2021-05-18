package canelhas.cars.common.exception;

import org.springframework.http.HttpStatus;

public class OperationException extends CustomException {
    private final HttpStatus status;

    public OperationException( String message ) {
        super( message );
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public OperationException( String message, HttpStatus httpStatus ) {
        super( message, httpStatus );
        this.status = httpStatus;
    }

    @Override public HttpStatus getStatus( ) {
        return status;
    }
}
