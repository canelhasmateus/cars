package canelhas.cars.common.utils;

import canelhas.cars.common.exception.ConflictException;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.exception.NotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.*;
import static canelhas.cars.common.functional.Adjectives.*;
import static canelhas.cars.common.functional.Verbs.raise;
import static canelhas.cars.common.utils.StringHelper.areEqual;
import static java.lang.String.format;

public class SearchHelper {

    public static int bestIndex( String value, List< String > searchList ) {
        return searchList.indexOf( bestMatch( value, searchList ) );
    }

    public static String bestMatch( String search, List< String > names ) {

        //region definitions
        Function< String, Boolean > containsSearch = partially( StringHelper::contained, search );
        //endregion

        final Collection< String > bestMatches = narrowingly( containsSearch ).apply( names );

        //region raise if not unique
        conditionally( raise( notFound( search ) ) )
                .on( bestMatches.isEmpty() );

        conditionally( raise( ambiguous( search, bestMatches ) ) )
                .on( bestMatches.size() > 1 );
        //endregion

        final var bestMatch = new ArrayList<>( bestMatches ).get( 0 );

        //region raise if not exact
        conditionally( raise( inexact( bestMatch, search ) ) )
                .on( !areEqual( search, bestMatch ) );
        //endregion

        return bestMatch;
    }

    public static Supplier< NotFoundException > notFound( String search ) {
        return lazily( NotFoundException::new,
                       format( NONE_FOUND, search ) );
    }

    public static Supplier< ConflictException > ambiguous( String search, Collection< String > possibles ) {
        final var possibilities = String.join( ", ", possibles );
        return lazily( ConflictException::new,
                       format( AMBIGUOUS_SEARCH, search, possibilities ) );
    }

    public static Supplier< DomainException > inexact( String bestMatch, String search ) {
        return lazily( DomainException::new,
                       format( INEXACT_SEARCH, search, bestMatch ) );
    }
    //region help

    //region monorepo

    private SearchHelper( ) {}

    //endregion

}
