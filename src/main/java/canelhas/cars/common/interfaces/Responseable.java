package canelhas.cars.common.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public interface Responseable< T > {

    HttpHeaders getHeaders( );

    JSONObject getBody( );

    String getUrl( );

    HttpMethod getMethod( );

    TypeReference< T > getResponseType( );
}
