package canelhas.cars.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
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

}
