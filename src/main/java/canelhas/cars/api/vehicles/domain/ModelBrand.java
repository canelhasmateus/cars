package canelhas.cars.api.vehicles.domain;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.utils.StringHelper;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static canelhas.cars.common.exception.ExceptionMessages.BRAND_REQUIRED;
import static canelhas.cars.common.functional.Adjectives.lazily;
import static canelhas.cars.common.utils.TypingHelper.maybe;


@RequiredArgsConstructor
public class ModelBrand {

    //FIXME : WHY CANT EXTEND VALUE_TYPE
    private final String value;

    public static ModelBrand of( String input ) {
        var value = maybe( input )
                            .map( StringHelper::normalize )
                            .map( StringHelper::toTitleCase )
                            .orElseThrow( required() );

        return new ModelBrand( value );
    }

    @JsonValue public String value( ) {
        return value;
    }

    //region exceptions

    public static Supplier< DomainException > required( ) {
        return lazily( DomainException::new, BRAND_REQUIRED );
    }
    //endregion

}
