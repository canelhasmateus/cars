package canelhas.cars.common.utils;

import java.util.regex.Pattern;

public class Regexes {

    public static final Pattern NOT_NUMERICAL    = Pattern.compile( "[^0-9]+" );
    public static final Pattern FIRST_NUMERICALS = Pattern.compile( "^([0-9]+)" );
    public static final Pattern ALPHA            = Pattern.compile( "^[a-zÀ-ÖØ-öø-ÿ ]+$", Pattern.CASE_INSENSITIVE );
    public static final Pattern PRICE            = Pattern.compile( "([0-9]++[.,]?)++$" );

    public static final Pattern BEARER = Pattern.compile( "(?:bearer +)?(\\S+)",
                                                          Pattern.CASE_INSENSITIVE );

    public static final Pattern EMAIL = Pattern.compile( "" +
                                                         "^([a-z0-9_]+\\.){0,}+" +
                                                         "([a-z0-9_]+){1}+" +
                                                         "@([a-z0-9_]+\\.){0,}+" +
                                                         "([a-z0-9_]+){1}+",
                                                         Pattern.CASE_INSENSITIVE );


    //region monorepo

    private Regexes( ) {}

    //endregion

}
