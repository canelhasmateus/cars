package canelhas.cars.api.auth.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder( toBuilder = true )
@Value
public class CarsSession {

    @JsonProperty( "token" )
    private final String token;

    public static CarsSession of( String token ) {
        return CarsSession.builder()
                          .token( token )
                          .build();
    }
}
