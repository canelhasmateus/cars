package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.utils.Regexes;
import canelhas.cars.common.utils.StringHelper;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.CONTAINS_INVALID_CHARACTERS;
import static canelhas.cars.api.util.ExceptionMessages.NAME_REQUIRED;
import static canelhas.cars.common.languaj.Adjectives.hopefully;
import static canelhas.cars.common.languaj.Adverbs.lazily;
import static canelhas.cars.common.utils.StringHelper.findWith;
import static canelhas.cars.common.utils.TypingHelper.optionalOf;
import static java.lang.String.format;

@RequiredArgsConstructor
public class ProperName extends ValueType< String > {

    //    CREATE SQL SERIALIZERS

    private final String value;

    public static ProperName of( String input ) {

        var value = optionalOf( input )
                            .map( StringHelper::normalize )
                            .map( StringHelper::toTitleCase )
                            .orElseThrow( required() );

        value = hopefully( findWith( Regexes.ALPHA ) ).apply( value )
                                                      .orElseThrow( invalid( value ) );

        return new ProperName( value );

    }

    @Override public String value( ) {
        return value;
    }


    //region exceptions
    private static Supplier< DomainException > required( ) {
        return lazily( DomainException::new, NAME_REQUIRED );
    }

    private static Supplier< DomainException > invalid( String value ) {
        return lazily( DomainException::new,
                       format( CONTAINS_INVALID_CHARACTERS, value ) );
    }
    //endregion

}
