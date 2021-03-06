package canelhas.cars.common.utils;

import canelhas.cars.common.exception.ConflictException;
import canelhas.cars.common.exception.NotFoundException;
import canelhas.cars.common.type.Nameable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.*;
import static canelhas.cars.common.languaj.Adjectives.*;
import static canelhas.cars.common.languaj.Verbs.raise;
import static java.lang.String.format;

public class SearchHelper {
    public static < T extends Nameable > T findBest( String inputSearch, List< T > candidates ) {
        final var findBestIndex = partially( SearchHelper::bestIndex, inputSearch );

        final var bestModelIndex = collectively( T::getName )
                                           .andThen( findBestIndex )
                                           .apply( candidates );

        return candidates.get( bestModelIndex );
    }

    private static int bestIndex( String value, List< String > searchList ) {
        return searchList.indexOf( bestMatch( value, searchList ) );
    }

    private static String bestMatch( String search, List< String > names ) {

        //region definitions
        final var containsSearch = partially( StringHelper::contained, search );
        final var equalsSearch   = partially( StringHelper::areEqual, search );
        //endregion

        final var nearMatches  = narrowingly( containsSearch ).apply( names );
        final var exactMatches = narrowingly( equalsSearch ).apply( nearMatches );

        raise( notFound( search ) ).when( nearMatches.isEmpty() );
        raise( ambiguous( search, nearMatches ) ).when( exactMatches.isEmpty() );

        return new ArrayList<>( exactMatches ).get( 0 );
    }


    //region exceptions
    public static Supplier< NotFoundException > notFound( String search ) {
        return lazily( NotFoundException::new,
                       format( NONE_FOUND, search ) );
    }

    public static Supplier< ConflictException > ambiguous( String search, Collection< String > candidates ) {

        //region definitions
        final var possibilities = String.join( ", ", candidates );

        final var many = lazily( ConflictException::new,
                                 format( AMBIGUOUS_SEARCH, search, possibilities ) );

        final var single = lazily( ConflictException::new,
                                   format( INEXACT_SEARCH, search, possibilities ) );
        //endregion

        return conditionally( single )
                       .when( candidates.size() == 1 )
                       .orElse( many );

    }
    //endregion


    //region monorepo

    private SearchHelper( ) {}

    //endregion

}
