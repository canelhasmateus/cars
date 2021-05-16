package canelhas.cars.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DomainException extends CarsException {
    private final HttpStatus status;

    public DomainException( String message ) {
        super( message );
        this.status = HttpStatus.UNPROCESSABLE_ENTITY;
    }

    public DomainException( String message, HttpStatus httpStatus ) {
        super( message );
        this.status = httpStatus;
    }
}
