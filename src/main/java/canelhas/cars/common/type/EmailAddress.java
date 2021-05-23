package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.languaj.noun.ValueType;
import canelhas.cars.common.utils.Regexes;
import canelhas.cars.common.utils.StringHelper;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.EMAIL_REQUIRED;
import static canelhas.cars.api.util.ExceptionMessages.IS_A_INVALID_EMAIL;
import static canelhas.cars.common.languaj.Adjectives.hopefully;
import static canelhas.cars.common.languaj.Adjectives.lazily;
import static canelhas.cars.common.utils.StringHelper.findWith;
import static canelhas.cars.common.utils.TypingHelper.optionalOf;
import static java.lang.String.format;


@RequiredArgsConstructor
public class EmailAddress extends ValueType< String > {

    private final String value;

    public static EmailAddress of( String input ) {

        var value = optionalOf( input )
                            .map( StringHelper::normalize )
                            .orElseThrow( required() );

        value = hopefully( findWith( Regexes.EMAIL ) )
                        .apply( value )
                        .orElseThrow( invalid( value ) );

        return new EmailAddress( value );

    }


    @Override public String value( ) {
        return value;
    }

    //region exceptions
    private static Supplier< DomainException > required( ) {
        return lazily( DomainException::new, EMAIL_REQUIRED );
    }

    private static Supplier< DomainException > invalid( String value ) {
        return lazily( DomainException::new,
                       format( IS_A_INVALID_EMAIL, value ) );
    }
    //endregion

}
