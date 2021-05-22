package canelhas.cars.api.vehicles.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.type.ValueType;
import canelhas.cars.common.utils.StringHelper;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.BRAND_REQUIRED;
import static canelhas.cars.common.languaj.Adverbs.lazily;
import static canelhas.cars.common.utils.TypingHelper.optionalOf;


@RequiredArgsConstructor
public class ModelBrand extends ValueType< String > {

    private final String value;

    public static ModelBrand of( String input ) {
        var value = optionalOf( input )
                            .map( StringHelper::normalize )
                            .map( StringHelper::toTitleCase )
                            .orElseThrow( required() );

        return new ModelBrand( value );
    }

    public String value( ) {
        return value;
    }

    //region exceptions

    public static Supplier< DomainException > required( ) {
        return lazily( DomainException::new, BRAND_REQUIRED );
    }
    //endregion

}
