package canelhas.cars.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends CarsException {

    private final HttpStatus status;

    public NotFoundException( String message ) {
        super( message );
        this.status = HttpStatus.NOT_FOUND;
    }


    public NotFoundException( String message, HttpStatus httpStatus ) {
        super( message );
        this.status = httpStatus;
    }
}
