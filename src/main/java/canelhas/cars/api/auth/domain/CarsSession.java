package canelhas.cars.api.auth.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder( toBuilder = true )
@Getter
public class CarsSession {

    @JsonProperty( "token" )
    private final String token;

}