package canelhas.cars.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonHelper {

    //region monorepo

    private JsonHelper( ) {}

    //endregion

    private static final ObjectMapper mapper = new ObjectMapper()
                                                       .configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

    @SneakyThrows
    public static < T > T map( Class< T > clazz, String content ) {
        return mapper.readValue( content, clazz );
    }

    @SneakyThrows
    public static < T > T map( TypeReference< T > type, String content ) {
        return mapper.readValue( content, type );
    }

}
