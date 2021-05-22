package canelhas.cars.common.utils;

import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringHelper {

    //region monorepo

    private StringHelper( ) {}

    public static String convert( String body, Charset charset ) {

        return new String( body.getBytes( charset ), charset );

    }
    //endregion

    public static String toTitleCase( String name ) {
        return Stream.of( name.trim().toLowerCase().split( " " ) )
                     .map( StringUtils::capitalize )
                     .collect( Collectors.joining( " " ) );

    }


    public static Function< String, String > findWith( Pattern pattern ) {
        return s -> {
            final var matcher = pattern.matcher( s );
            matcher.find();
            return matcher.group();
        };

    }

    public static boolean contains( String container, String containee ) {
        return container.toLowerCase().contains( containee.toLowerCase() );
    }

    public static boolean contained( String containee, String container ) {
        return contains( container, containee );
    }

    public static boolean areEqual( String name, String value ) {
        return name.equalsIgnoreCase( value );
    }

    public static String normalize( String s ) {
        return s.trim().toLowerCase();
    }

}
