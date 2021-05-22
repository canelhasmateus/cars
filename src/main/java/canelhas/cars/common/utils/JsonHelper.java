package canelhas.cars.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

    //region monorepo

    private JsonHelper( ) {}

    //endregion

    private static final ObjectMapper mapper = new ObjectMapper()
                                                       .configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

    public static < T > T map( Class< T > clazz, String serialized ) {
        
        try {
            return mapper.readValue( serialized, clazz );
        }
        catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }
    }

    public static < T > T map( TypeReference< T > clazz, String serialized ) {
        try {
            return mapper.readValue( serialized, clazz );
        }
        catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }
    }

}
