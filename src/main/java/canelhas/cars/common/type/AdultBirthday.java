package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.languaj.Verbs;
import canelhas.cars.common.languaj.noun.ValueType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.*;
import static canelhas.cars.common.languaj.Adjectives.conditionally;
import static canelhas.cars.common.languaj.Adjectives.lazily;
import static canelhas.cars.common.utils.DateHelper.findAge;
import static java.lang.String.format;


@RequiredArgsConstructor
public class AdultBirthday extends ValueType< Date > {

    private final Date value;


    public static AdultBirthday of( Date input ) {

        Optional.ofNullable( input )
                .orElseThrow( required() );

        final var age = findAge( new Date(), input );

        conditionally( benjaminButton( age ) )
                .when( age <= 0 )
                .map( Verbs::raise );

        conditionally( tooBaby( age ) )
                .when( age <= 18 )
                .map( Verbs::raise );

        conditionally( immortal( age ) )
                .when( age >= 100 )
                .map( Verbs::raise );

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
