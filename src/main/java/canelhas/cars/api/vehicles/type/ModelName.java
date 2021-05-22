package canelhas.cars.api.vehicles.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.type.ValueType;
import canelhas.cars.common.utils.StringHelper;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.MODEL_REQUIRED;
import static canelhas.cars.common.languaj.Adverbs.lazily;
import static canelhas.cars.common.utils.StringHelper.toTitleCase;
import static canelhas.cars.common.utils.TypingHelper.optionalOf;


@RequiredArgsConstructor
public class ModelName extends ValueType< String > {

    private final String value;

    public static ModelName of( String input ) {

        final var value = optionalOf( input )
                                  .map( StringHelper::normalize )
                                  .orElseThrow( required() );

        return new ModelName( toTitleCase( value ) );
    }

    public String value( ) {
        return value;
    }

    public static Supplier< DomainException > required( ) {
        return lazily( DomainException::new, MODEL_REQUIRED );
    }


}
