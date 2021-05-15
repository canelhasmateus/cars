package canelhas.cars.common.functional;

import canelhas.cars.common.exception.CarsException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationTest {

    static String                     message  = "Message!";
    static Function< Object, Object > identity = string -> string;
    static Function< Object, Object > error    = __ -> {
        throw new RuntimeException( message );
    };

    @Test
    public void givenErrorsThenRethrowsWhenCalled( ) {

        var validation = new Validation( HttpStatus.UNPROCESSABLE_ENTITY );
        validation.map( message, error );
        validation.map( message, error );

        assertThrows( CarsException.class, validation::verify );

    }

    @Test
    public void givenErrorsThenMergeMessages( ) {

        var validation = new Validation( HttpStatus.UNPROCESSABLE_ENTITY );
        validation.map( message, error );
        validation.map( message, error );

        try { validation.verify(); }
        catch ( Exception e ) {
            assertEquals( e.getMessage(), message + "\n" + message );
        }

    }


    @Test
    public void givenValidThenReturnsResult( ) {

        var validation = new Validation( HttpStatus.UNPROCESSABLE_ENTITY );
        var result     = validation.map( message, identity );

        assertEquals( message, result );
    }


}