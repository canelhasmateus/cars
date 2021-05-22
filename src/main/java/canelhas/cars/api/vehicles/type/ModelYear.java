package canelhas.cars.api.vehicles.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.type.ValueType;
import canelhas.cars.common.utils.Regexes;
import canelhas.cars.common.utils.StringHelper;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.COULD_NOT_PARSE_YEAR;
import static canelhas.cars.api.util.ExceptionMessages.YEAR_REQUIRED;
import static canelhas.cars.common.languaj.Adjectives.hopefully;
import static canelhas.cars.common.languaj.Adverbs.lazily;
import static canelhas.cars.common.utils.StringHelper.findWith;
import static canelhas.cars.common.utils.TypingHelper.optionalOf;
import static java.lang.String.format;


@RequiredArgsConstructor
public class ModelYear extends ValueType< String > {

    private final String value;

    public static ModelYear of( String input ) {

        final var value = optionalOf( input )
                                  .map( StringHelper::normalize )
                                  .map( StringHelper::toTitleCase )
                                  .orElseThrow( ModelYear.required() );

        return new ModelYear( value );
    }

    public String value( ) {
        return value;
    }

    public static int asInteger( ModelYear input ) {
        final var value = input.value();
        return hopefully( findWith( Regexes.FIRST_NUMERICALS ) )
                       .apply( value )
                       .map( Integer::parseInt )
                       .orElseThrow( couldNotParse( value ) );

    }

    //region exceptions
    private static Supplier< DomainException > couldNotParse( String value ) {
        return lazily( DomainException::new,
                       format( COULD_NOT_PARSE_YEAR, value ) );
    }

    public static Supplier< DomainException > required( ) {
        return lazily( DomainException::new, YEAR_REQUIRED );
    }
    //endregion
}
