package canelhas.cars.api.vehicles.domain;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.utils.StringHelper;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static canelhas.cars.common.exception.ExceptionMessages.MODEL_REQUIRED;
import static canelhas.cars.common.functional.Adjectives.lazily;
import static canelhas.cars.common.utils.TypingHelper.maybe;


@RequiredArgsConstructor
public class ModelName {
    //FIXME : WHY CANT EXTEND VALUE_TYPE
    private final String value;

    public static ModelName of( String input ) {
        final var value = maybe( input )
                                  .map( StringHelper::normalize )
                                  .map( StringHelper::toTitleCase )
                                  .orElseThrow( required() );
        return new ModelName( value );
    }


    @JsonValue
    public String value( ) {
        return value;
    }

    public static Supplier< DomainException > required( ) {
        return lazily( DomainException::new, MODEL_REQUIRED );
    }


}
