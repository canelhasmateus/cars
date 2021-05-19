package canelhas.cars.api.vehicles.domain;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.utils.StringHelper;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

import static canelhas.cars.common.functional.Adjectives.lazily;
import static canelhas.cars.common.utils.TypingHelper.maybe;


@RequiredArgsConstructor
public class ModelYear {
    //FIXME : WHY CANT EXTEND VALUE_TYPE
    private final String value;

    public static ModelYear of( String input ) {

        final var value = maybe( input ).map( StringHelper::normalize )
                                        .orElseThrow( ModelYear.required() );

        return new ModelYear( value );
    }


    @JsonValue
    public String value( ) {
        return value;
    }

    public static int asInteger( ModelYear input ) {
        // TODO: 17/05/2021 fazer direito
        final var value = input.value();
        final var year  = value.split( "-" )[ 0 ];
        return Integer.parseInt( year );
    }

    //region exceptions
    public static Supplier< DomainException > invalidValue( String input ) {
        return lazily( DomainException::new,
                       input + "não é um valor válido de ano do veiculo." );

    }

    public static Supplier< DomainException > required( ) {
        return lazily( DomainException::new,
                       "Informe algum valor de ano do veículo." );
    }
    //endregion
}
