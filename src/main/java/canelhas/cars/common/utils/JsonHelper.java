package canelhas.cars.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;

public class JsonHelper {

    //region monorepo

    private JsonHelper(){}
    //endregion
    private static final ObjectMapper mapper = new ObjectMapper();


    public static < T > T map( Class< T > clazz, String serialized ) {
        try {
            return mapper.readValue( serialized, clazz );
        }
        catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }
    }

    public static < C extends Collection< ? >, V > Collection< V > map( Class< C > collection, Class< V > valueType, String serialized ) {

        final var collectionType1 = mapper.getTypeFactory().constructCollectionType( collection, valueType );

        try {
            return mapper.readValue( serialized, collectionType1 );
        }
        catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }
    }
}
