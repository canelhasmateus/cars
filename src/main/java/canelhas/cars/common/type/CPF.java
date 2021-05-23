package canelhas.cars.common.type;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.languaj.noun.Chain;
import canelhas.cars.common.languaj.noun.ValueType;
import canelhas.cars.common.utils.Regexes;
import canelhas.cars.common.utils.StringHelper;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static canelhas.cars.api.util.ExceptionMessages.*;
import static canelhas.cars.common.languaj.Adjectives.lazily;
import static canelhas.cars.common.languaj.Verbs.raise;
import static canelhas.cars.common.utils.TypingHelper.optionalOf;
import static java.lang.String.format;

@RequiredArgsConstructor
public class CPF extends ValueType< String > {

    private final String value;

    @Override public String value( ) {
        return value;
    }

    public static CPF of( String input ) {

        final var value = optionalOf( input )
                                  .map( StringHelper::normalize )
                                  .orElseThrow( CPF.required() );

        //region definitions
        UnaryOperator< String > keepNumericals = s -> Regexes.NOT_NUMERICAL.matcher( s ).replaceAll( " " );
        UnaryOperator< String > checkLength = s -> {
            if ( s.length() != 11 ) {
                throw CPF.invalidLength( s ).get();
            }
            return s;
        };
        //endregion

        return Chain.of( keepNumericals )
                    .andThen( checkLength )
                    .andThen( CPF::validateDigits )
                    .andThen( CPF::new )
                    .apply( value );

    }

    //region help
    private static String validateDigits( String input ) {

//        NAO ANIMEI FAZER ESSA PARTE SEM IF / ELSE, SORRY
        char dig10;
        char dig11;
        int  i;
        int  num;
        var  sm   = 0;
        var  peso = 10;

        raise( CPF.invalidValue( input ) )
                .when( input.chars().distinct().count() == 1 );

        for ( i = 0; i < 9; i++ ) {
            num  = ( input.charAt( i ) - 48 );
            sm   = sm + ( num * peso );
            peso = peso - 1;
        }

        int r = 11 - ( sm % 11 );
        if ( ( r == 10 ) || ( r == 11 ) ) { dig10 = '0'; }
        else { dig10 = ( char ) ( r + 48 ); }

        sm   = 0;
        peso = 11;
        for ( i = 0; i < 10; i++ ) {
            num  = input.charAt( i ) - 48;
            sm   = sm + ( num * peso );
            peso = peso - 1;
        }

        r = 11 - ( sm % 11 );
        if ( ( r == 10 ) || ( r == 11 ) ) { dig11 = '0'; }
        else { dig11 = ( char ) ( r + 48 ); }
        if ( !( dig10 == input.charAt( 9 ) &&
                dig11 == input.charAt( 10 ) ) ) {
            throw CPF.invalidValue( input ).get();
        }

        return input;

    }
    //endregion

    //region exception
    public static Supplier< DomainException > invalidValue( String input ) {
        return lazily( DomainException::new,
                       format( INVALID_CPF_VALUE, input ) );
    }

    public static Supplier< DomainException > required( ) {
        return lazily( DomainException::new, CPF_REQUIRED );
    }

    public static Supplier< DomainException > invalidLength( String input ) {
        return lazily( DomainException::new,
                       format( INVALID_CPF_LENGTH, input ) );
    }

    //endregion

}
