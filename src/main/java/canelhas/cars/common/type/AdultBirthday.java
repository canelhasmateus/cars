package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.functional.Flux;
import canelhas.cars.common.utils.DateHelper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;

import java.util.Date;
import java.util.function.UnaryOperator;

import static canelhas.cars.common.exception.ExceptionMessages.*;


@Builder( toBuilder = true )
public class AdultBirthday extends ValueType< Date > {

    private final Date value;


    public static AdultBirthday of( Date input ) {

        if ( input == null ) {
            throw new DomainException( BIRTHDAY_REQUIRED );
        }

        //region definitions
        UnaryOperator< Date > throwOnTooRecent = date -> {
            int diff = DateHelper.yearDifference( new Date(), date );

            if ( diff <= 0 ) {
                throw new DomainException( diff + ARE_YOU_BENJAMIN_BUTTON );
            }
            else if ( diff < 18 ) {
                throw new DomainException( diff + IS_TOO_EARLY );
            }
            return date;
        };
        //endregion

        var birthday = Flux.of( throwOnTooRecent )
                           .apply( input );

        return new AdultBirthday( birthday );
    }

    @JsonValue
    @JsonFormat( pattern = "yyyy-MM-dd", timezone = "Brazil/East" )
    @Override public Date value( ) {
        return value;
    }


}
