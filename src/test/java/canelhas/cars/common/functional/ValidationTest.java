package canelhas.cars.common.functional;

import canelhas.cars.common.exception.CustomException;
import canelhas.cars.common.exception.DomainException;
import org.junit.jupiter.api.Test;

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
    void givenErrorsThenRethrowsWhenCalled( ) {

        var validation = new Validation( DomainException::new );
        validation.assemble( message, error );
        validation.assemble( message, error );

        assertThrows( CustomException.class, validation::verify );

    }

    @Test
    void givenErrorsThenMergeMessages( ) {

        var validation = new Validation( DomainException::new );
        validation.assemble( message, error );
        validation.assemble( message, error );

        try { validation.verify(); }
        catch ( Exception e ) {
            assertEquals( e.getMessage(), message + "\n" + message );
        }

    }


    @Test
    void givenValidThenReturnsResult( ) {

        var validation = new Validation( DomainException::new );
        var result     = validation.assemble( message, identity );

        assertEquals( message, result );
    }


}