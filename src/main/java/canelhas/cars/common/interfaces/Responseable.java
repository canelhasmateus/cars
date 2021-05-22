package canelhas.cars.common.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.Optional;

public interface Responseable< T > {

    HttpHeaders getHeaders( );

    Optional< Serializable > getBody( );

    String getUrl( );

    HttpMethod getMethod( );

    TypeReference< T > getResponseType( );
}
