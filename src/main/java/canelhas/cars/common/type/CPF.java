package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.functional.Flux;
import canelhas.cars.common.namespace.Regexes;
import lombok.Builder;

import java.util.function.UnaryOperator;

import static canelhas.cars.common.exception.ExceptionMessages.*;

@Builder( toBuilder = true )
public class CPF implements ValueType< String > {

    private final String value;

    @Override public String value( ) {
        return value;
    }

    public static CPF of( String input ) {

        if ( input == null ) {
            throw new DomainException( CPF_REQUIRED );
        }

        //region definitions
        UnaryOperator< String > keepNumericals = s -> Regexes.NOT_NUMERICAL.matcher( s ).replaceAll( " " );

        UnaryOperator< String > throwOnIncorrectLength = s -> {
            if ( s.length() != 11 ) {
                throw new DomainException( INVALID_CPF_LENGTH );
            }
            return s;
        };
        //endregion

        var cpf = Flux.of( keepNumericals )
                      .andThen( throwOnIncorrectLength )
                      .andThen( CPF::validateDigits )
                      .apply( input );

        return new CPF( cpf );
    }

    public static String validateDigits( String input ) {

        char dig10;
        char dig11;
        int  i;
        int  num;
        var  sm   = 0;
        var  peso = 10;

        if ( input.chars().distinct().count() == 1 ) {
            throw new DomainException( input + IS_A_INVALID_CPF );
        }

        for ( i = 0; i < 9; i++ ) {
            num = ( input.charAt( i ) - 48 );
            sm = sm + ( num * peso );
            peso = peso - 1;
        }

        int r = 11 - ( sm % 11 );
        if ( ( r == 10 ) || ( r == 11 ) ) { dig10 = '0'; }
        else { dig10 = ( char ) ( r + 48 ); }

        sm = 0;
        peso = 11;
        for ( i = 0; i < 10; i++ ) {
            num = input.charAt( i ) - 48;
            sm = sm + ( num * peso );
            peso = peso - 1;
        }

        r = 11 - ( sm % 11 );
        if ( ( r == 10 ) || ( r == 11 ) ) { dig11 = '0'; }
        else { dig11 = ( char ) ( r + 48 ); }
        if ( !( dig10 == input.charAt( 9 ) &&
                dig11 == input.charAt( 10 ) ) ) {
            throw new DomainException( input + IS_A_INVALID_CPF );
        }

        return input;

    }
}
