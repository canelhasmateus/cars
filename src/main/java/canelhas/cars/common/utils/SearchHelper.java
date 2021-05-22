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
import static canelhas.cars.common.languaj.Adjectives.narrowingly;
import static canelhas.cars.common.languaj.Adjectives.partially;
import static canelhas.cars.common.languaj.Adverbs.conditionally;
import static canelhas.cars.common.languaj.Adverbs.lazily;
import static canelhas.cars.common.languaj.Verbs.raise;
import static java.lang.String.format;

public class SearchHelper {

    public static int bestIndex( String value, List< String > searchList ) {
        return searchList.indexOf( bestMatch( value, searchList ) );
    }

    public static String bestMatch( String search, List< String > names ) {

        //region definitions
        Function< String, Boolean > containsSearch = partially( StringHelper::contained, search );
        Function< String, Boolean > equalsSearch   = partially( StringHelper::areEqual, search );
        //endregion

        final var nearMatches  = narrowingly( containsSearch ).apply( names );
        final var exactMatches = narrowingly( equalsSearch ).apply( nearMatches );

        //region early returns
        conditionally( raise( notFound( search ) ) )
                .on( nearMatches.isEmpty() );

        conditionally( raise( ambiguous( search, nearMatches ) ) )
                .on( exactMatches.isEmpty() );

        conditionally( raise( ambiguous( search, exactMatches ) ) )
                .on( exactMatches.size() > 1 );
        //endregion

        return new ArrayList<>( exactMatches ).get( 0 );
    }

    public static Supplier< NotFoundException > notFound( String search ) {
        return lazily( NotFoundException::new,
                       format( NONE_FOUND, search ) );
    }

    public static Supplier< ConflictException > ambiguous( String search, Collection< String > candidates ) {

        final var possibilities = String.join( ", ", candidates );

        final var many = lazily( ConflictException::new,
                                 format( AMBIGUOUS_SEARCH, search, possibilities ) );

        final var single = lazily( ConflictException::new,
                                   format( INEXACT_SEARCH, search, possibilities ) );

        return ( ) -> conditionally( single, many )
                              .on( candidates.size() == 1 );
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
