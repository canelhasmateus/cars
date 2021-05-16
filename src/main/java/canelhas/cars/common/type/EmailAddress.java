package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.namespace.Regexes;
import lombok.Builder;

import java.util.regex.Pattern;

import static canelhas.cars.common.exception.ExceptionMessages.EMAIL_REQUIRED;
import static canelhas.cars.common.exception.ExceptionMessages.IS_A_INVALID_EMAIL;


@Builder( toBuilder = true )
public class EmailAddress extends ValueType< String > {
    public static final Pattern emailPattern = Regexes.EMAIL;
    private final       String  value;

    public static EmailAddress of( String value ) {

        if ( value == null ) {
            throw new DomainException( EMAIL_REQUIRED );
        }

        value = value.trim().toLowerCase();

        var matcher = emailPattern.matcher( value );

        if ( !matcher.matches() ) {
            throw new DomainException( value + IS_A_INVALID_EMAIL );
        }

        return new EmailAddress( value );
    }

    @Override public String value( ) {
        return value;
    }
}
