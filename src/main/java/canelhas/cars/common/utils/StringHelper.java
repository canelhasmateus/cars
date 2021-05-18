package canelhas.cars.common.utils;

import org.springframework.util.StringUtils;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringHelper {

    //region monorepo

    private StringHelper( ) {}
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

}
