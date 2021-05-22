package canelhas.cars.common.utils;

import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.interfaces.Responseable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@RequiredArgsConstructor
public class RequestHelper {

    private final RestTemplate restTemplate;

    public < T > ResponseEntity< T > doRequest( Responseable< T > responseable ) {
        return request( restTemplate, responseable );
    }

    //region help
    public static < T > ResponseEntity< T > request( RestTemplate template, Responseable< T > responseable ) {

        //region definitions
        final var url        = responseable.getUrl();
        final var method     = responseable.getMethod();
        final var httpEntity = new HttpEntity<>( responseable.getBody().toString(), responseable.getHeaders() );

        //endregion

        final var response = template.exchange( url, method, httpEntity, String.class );

        T typedBody = null;
        if ( response.getBody() != null ) {
            final var converted = StringHelper.convert( response.getBody(), StandardCharsets.UTF_8 );
            typedBody = JsonHelper.map( responseable.getResponseType(), converted );
        }

        return new ResponseEntity<>( typedBody, response.getHeaders(), response.getStatusCode() );

    }

    public static < K, V > Function< K, V > successfully( Function< K, ResponseEntity< V > > request ) {
        return ( K k ) -> {
            final ResponseEntity< V > response = request.apply( k );
            if ( !String.valueOf( response.getStatusCodeValue() ).startsWith( "2" ) ) {
                throw new DomainException( "Um recurso dependente falhou. Tente novamente mais tarde",
                                           HttpStatus.BAD_GATEWAY );
            }
            return response.getBody();
        };
    }
}
