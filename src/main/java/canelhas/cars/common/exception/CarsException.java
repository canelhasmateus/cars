package canelhas.cars.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CarsException extends RuntimeException {

    private final HttpStatus status;

    public CarsException( String message ) {
        super( message );
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }


    public CarsException( String message, HttpStatus httpStatus ) {
        super( message );
        this.status = httpStatus;
    }
}
