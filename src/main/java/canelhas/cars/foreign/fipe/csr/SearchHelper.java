package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.common.exception.ConflictException;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.exception.NotFoundException;
import canelhas.cars.common.exception.OperationException;
import canelhas.cars.common.utils.StringHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static canelhas.cars.common.functional.Adjectives.*;
import static canelhas.cars.common.functional.Verbs.raise;
import static canelhas.cars.common.utils.StringHelper.areEqual;
import static java.lang.String.format;

public class SearchHelper {

    //region monorepo

    private SearchHelper( ) {}

    //endregion

    static int bestIndex( String value, List< String > searchList ) {
        return searchList.indexOf( bestMatch( value, searchList ) );
    }

    public static String bestMatch( String search, List< String > names ) {

        //region definitions

        // TODO: 19/05/2021 remove control flow, remove lambda.
        Function< String, Integer >                  findDistance = name -> StringHelper.contains( name, search ) ? 0 : 1;
        final BiFunction< Integer, String, Boolean > isBest       = ( bestScore, candidate ) -> findDistance.apply( candidate ).equals( bestScore );
        //endregion

        final var bestDistance = collectively( findDistance )
                                         .apply( names ).stream()
                                         .min( Comparator.comparingInt( p -> p ) )
                                         .orElseThrow( error( search ) );

        conditionally( raise( notFound( search ) ) )
                .on( bestDistance != 0 );

        final var isBestDistance = partially( isBest, bestDistance );

        Collection< String > bestMatches = narrowingly( isBestDistance ).apply( names );

        conditionally( raise( ambiguous( search, bestMatches ) ) )
                .on( bestMatches.size() > 1 );


        final var bestMatch = new ArrayList<>( bestMatches ).get( 0 );


        conditionally( raise( ambiguous( search, bestMatch ) ) )
                .on( !areEqual( search, bestMatch ) );


        return bestMatch;
    }

    public static Supplier< NotFoundException > notFound( String search ) {
        return lazily( NotFoundException::new,
                       format( "Não foram encontrados registros para a pesquisa %s", search ) );
    }

    public static Supplier< ConflictException > ambiguous( String search, Collection< String > possibles ) {
        final var possibilities = String.join( ", ", possibles );
        return lazily( ConflictException::new,
                       format( "%s é um pouco ambiguo... Os registros mais proximos são %s", search, possibilities ) );
    }

    public static Supplier< DomainException > ambiguous( String search, String bestMatch ) {
        return lazily( DomainException::new,
                       format( "%s? você quis dizer %s?", search, bestMatch ) );
    }

    public static Supplier< OperationException > error( String search ) {
        return lazily( OperationException::new,
                       format( "Ocorreu um erro durante a pesquisa de %s", search ) );
    }

    //region help

}
