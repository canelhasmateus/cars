package canelhas.cars.common.utils;

import canelhas.cars.common.exception.OperationException;
import canelhas.cars.common.languaj.noun.Chain;
import canelhas.cars.common.type.Responseable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static canelhas.cars.api.util.ExceptionMessages.UNSUCCESSFUL_REQUEST;
import static canelhas.cars.common.languaj.Adjectives.partially;
import static canelhas.cars.common.languaj.Verbs.raise;

@RequiredArgsConstructor
public class RequestHelper {

    public static < T > ResponseEntity< T > request( RestTemplate template, Responseable< T > responseable ) {

        //region definitions
        final var                   toUTF  = partially( StandardCharsets.UTF_8, StringHelper::convert );
        final Function< String, T > toType = partially( JsonHelper::map, responseable.getResponseType() );

        final var url    = responseable.getUrl();
        final var method = responseable.getMethod();

        final var body = responseable.getBody()
                                     .map( Object::toString )
                                     .orElse( null );
        final var httpEntity = new HttpEntity<>( body, responseable.getHeaders() );
        //endregion

        final var response = template.exchange( url, method, httpEntity, String.class );

        final var typedBody = Optional.ofNullable( response.getBody() )
                                      .map( toUTF )
                                      .map( toType )
                                      .orElse( null );

        return new ResponseEntity<>( typedBody, response.getHeaders(), response.getStatusCode() );
    }

    public static < K, V > Function< K, V > successfully( Function< K, ResponseEntity< V > > request ) {
        return ( K k ) -> {

            final var response = request.apply( k );

            //region definitions
            final var firstResponseCodeChar = Chain.of( String::valueOf )
                                                   .andThen( s -> s.charAt( 0 ) )
                                                   .apply( response.getStatusCodeValue() );
            //endregion

            raise( requestFailed() )
                    .when( firstResponseCodeChar != '2' );

            return response.getBody();
        };
    }

    //region exceptions
    public static Supplier< OperationException > requestFailed( ) {
        return ( ) -> new OperationException( UNSUCCESSFUL_REQUEST, HttpStatus.BAD_GATEWAY );
    }
    //endregion

    //region help
    private final RestTemplate restTemplate;

    public < T > ResponseEntity< T > request( Responseable< T > responseable ) {
        return request( restTemplate, responseable );
    }
    //endregion

}
