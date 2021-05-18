package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.utils.Regexes;
import canelhas.cars.common.utils.StringHelper;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;
import java.util.regex.Pattern;

import static canelhas.cars.common.exception.ExceptionMessages.EMAIL_REQUIRED;
import static canelhas.cars.common.exception.ExceptionMessages.IS_A_INVALID_EMAIL;
import static canelhas.cars.common.functional.Adjectives.conditionally;
import static canelhas.cars.common.functional.Adjectives.lazily;
import static canelhas.cars.common.functional.Verbs.raise;
import static canelhas.cars.common.utils.TypingHelper.maybe;


@RequiredArgsConstructor
public class EmailAddress extends ValueType< String > {
    public static final Pattern emailPattern = Regexes.EMAIL;
    private final       String  value;

    public static EmailAddress of( String value ) {

        value = maybe( value ).map( StringHelper::normalize )
                              .orElseThrow( required() );

        var matcher = emailPattern.matcher( value );

        conditionally( raise( invalid( value ) ) )
                .on( !matcher.matches() );

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
        return lazily( DomainException::new, value + IS_A_INVALID_EMAIL );
    }
    //endregion

}
