package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.foreign.fipe.domain.FipeModel;
import canelhas.cars.foreign.fipe.domain.FipeYear;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder( toBuilder = true )
@Getter
public class FipeModelResponse {

    private final List< FipeModel > models;
    private final List< FipeYear >  years;

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static FipeModelResponse of( @JsonProperty( "modelos" ) List< FipeModel > models,
                                        @JsonProperty( "anos" ) List< FipeYear > years ) {

        return FipeModelResponse.builder()
                                .models( models )
                                .years( years )
                                .build();
    }


}
