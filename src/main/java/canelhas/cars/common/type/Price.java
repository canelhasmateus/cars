package canelhas.cars.common.type;

import canelhas.cars.api.util.ExceptionMessages;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.languaj.Verbs;
import canelhas.cars.common.languaj.noun.ValueType;
import canelhas.cars.common.utils.Regexes;
import canelhas.cars.common.utils.StringHelper;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.INVALID_PRICE;
import static canelhas.cars.common.languaj.Adjectives.*;
import static canelhas.cars.common.utils.StringHelper.findWith;
import static canelhas.cars.common.utils.TypingHelper.optionalOf;
import static java.lang.String.format;

@RequiredArgsConstructor
public class Price extends ValueType< BigDecimal > {
    private final BigDecimal value;

    public static Price of( String input ) {

        var value = optionalOf( input )
                            .map( StringHelper::normalize )
                            .orElseThrow( required() );

        value = hopefully( findWith( Regexes.PRICE ) )
                        .apply( value )
                        .orElseThrow( invalid( value ) );

        value = value.replace( ".", "" )
                     .replace( ",", "." );

        return Price.of( Double.parseDouble( value ) );
    }

    public static Price of( Number input ) {

        final var value = Optional.ofNullable( input )
                                  .map( Number::doubleValue )
                                  .map( BigDecimal::valueOf )
                                  .orElseThrow( required() );

        conditionally( isNegative( value ) )
                .when( input.doubleValue() < 0 )
                .map( Verbs::raise );

        return new Price( value );
    }

    private static Supplier< DomainException > isNegative( Number input ) {
        return lazily( DomainException::new,
                       format( ExceptionMessages.NEGATIVE_PRICE, input ) );
    }

    private static Supplier< DomainException > invalid( String value ) {
        return lazily( DomainException::new,
                       format( INVALID_PRICE, value ) );
    }

    private static Supplier< DomainException > required( ) {
        return lazily( DomainException::new,
                       "Informe um valor de preço." );
    }


    @Override public BigDecimal value( ) {
        return value;
    }
}
