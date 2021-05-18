package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.namespace.Regexes;
import lombok.Builder;

import java.util.Optional;
import java.util.function.Supplier;

import static canelhas.cars.common.functional.Adjectives.hopefully;
import static canelhas.cars.common.functional.Adjectives.lazily;
import static canelhas.cars.common.utils.StringHelper.findWith;


@Builder( toBuilder = true )
public class ModelYear extends ValueType< String > {
    private final String value;

    public static ModelYear of( String input ) {

        Optional.ofNullable( input )
                .orElseThrow( ModelYear.required() );

        //definitions
        final var toModelYear = Chain.of( String::trim )
                                     .andThen( findWith( Regexes.VEHICLE_YEAR ) )
                                     .andThen( ModelYear::new );
        //endregion

        return hopefully( toModelYear )
                       .apply( input )
                       .orElseThrow( ModelYear.invalidValue( input ) );


    }


    @Override public String value( ) {
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
