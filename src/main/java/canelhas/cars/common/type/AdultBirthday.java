package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.*;
import static canelhas.cars.common.languaj.Adverbs.conditionally;
import static canelhas.cars.common.languaj.Adverbs.lazily;
import static canelhas.cars.common.languaj.Verbs.raise;
import static canelhas.cars.common.utils.DateHelper.findAge;
import static java.lang.String.format;


@RequiredArgsConstructor
public class AdultBirthday extends ValueType< Date > {

    private final Date value;


    public static AdultBirthday of( Date input ) {

        Optional.ofNullable( input )
                .orElseThrow( required() );

        final var age = findAge( new Date(), input );
        conditionally( raise( benjaminButton( age ) ) )
                .on( age <= 0 );
        conditionally( raise( tooBaby( age ) ) )
                .on( age <= 18 );
        conditionally( raise( immortal( age ) ) )
                .on( age >= 100 );

        return new AdultBirthday( input );
    }

    private static Supplier< DomainException > immortal( Integer age ) {
        return lazily( DomainException::new,
                       format( ARE_YOU_IMMORTAL, age ) );
    }

    private static Supplier< DomainException > tooBaby( Integer age ) {
        return lazily( DomainException::new,
                       format( TOO_BABY, age ) );
    }

    private static Supplier< DomainException > benjaminButton( Integer age ) {
        return lazily( DomainException::new,
                       format( ARE_YOU_BENJAMIN_BUTTON, age ) );
    }

    public static Supplier< DomainException > required( ) {
        return lazily( DomainException::new, BIRTHDAY_REQUIRED );
    }

    @JsonValue
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "Brazil/East" )
    @Override public Date value( ) {
        return value;
    }


}
