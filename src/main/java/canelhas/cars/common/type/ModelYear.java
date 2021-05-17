package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.functional.Flux;
import canelhas.cars.common.namespace.Regexes;
import lombok.Builder;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;


@Builder( toBuilder = true )
public class ModelYear extends ValueType< String > {
    private final String value;

    public static ModelYear of( String input ) {

        if ( input == null ) {
            throw ModelYear.required().get();
        }

        //region definition
        final Function< Matcher, Optional< String > > findMatch = m -> {
            if ( m.find() ) {
                return Optional.of( m.group() );
            }
            return Optional.empty();
        };

        final Function< Optional< String >, ModelYear > createOrThrow = s -> s.map( ModelYear::new )
                                                                              .orElseThrow( ModelYear.invalidValue( input ) );
        //endregion

        return Flux.of( String::trim )
                   .andThen( Regexes.VEHICLE_YEAR::matcher )
                   .andThen( findMatch )
                   .andThen( createOrThrow )
                   .apply( input );


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

        return ( ) -> new DomainException( input + "não é um valor válido de ano do veiculo." );

    }

    public static Supplier< DomainException > required( ) {
        return ( ) -> new DomainException( "Informe algum valor de ano do veículo." );
    }


    //endregion
}
