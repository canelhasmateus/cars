package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.functional.Flux;
import canelhas.cars.common.namespace.Regexes;
import canelhas.cars.common.utils.StringHelper;
import lombok.Builder;

import java.util.function.Function;

import static canelhas.cars.common.exception.ExceptionMessages.CONTAINS_INVALID_CHARACTERS;
import static canelhas.cars.common.exception.ExceptionMessages.NAME_REQUIRED;

@Builder( toBuilder = true )
public class ProperName extends ValueType< String > {


    private final String value;

    public static ProperName of( String input ) {


        if ( input == null ) {
            throw new DomainException( NAME_REQUIRED );
        }


        Function< String, String > throwOnSpecialCharacters = s -> {
            if ( !Regexes.NOT_SPECIAL.matcher( s ).matches() ) {
                throw new DomainException( input + CONTAINS_INVALID_CHARACTERS );
            }

            return s;
        };

        var name = Flux.of( String::trim )
                       .andThen( throwOnSpecialCharacters )
                       .andThen( StringHelper::toTitleCase )
                       .apply( input );

        return new ProperName( name );
    }


    @Override public String value( ) {
        return value;
    }
}
