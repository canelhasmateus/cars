package canelhas.cars.common.namespace;

import java.util.regex.Pattern;

public class Regexes {
    public static final Pattern EMAIL         = Pattern.compile( "" +
                                                                 "^([a-z0-9_]+\\.){0,}" +
                                                                 "([a-z0-9_]+){1}" +
                                                                 "@([a-z0-9_]+\\.){0,}" +
                                                                 "([a-z0-9_]+){1}",
                                                                 Pattern.CASE_INSENSITIVE );
    public static final Pattern NOT_NUMERICAL = Pattern.compile( "" +
                                                                 "[^0-9]+" );

    public static final Pattern NOT_SPECIAL = Pattern.compile( "[a-z' À-ú-]+",
                                                               Pattern.CASE_INSENSITIVE );

}
