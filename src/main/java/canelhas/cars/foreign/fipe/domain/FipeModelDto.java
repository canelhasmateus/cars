package canelhas.cars.foreign.fipe.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder( toBuilder = true )
public class FipeModelDto {

    List< FipeModel > model;
    List< FipeYear >  year;

    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static FipeModelDto of( @JsonProperty( "modelos" ) List< FipeModel > model,
                                   @JsonProperty( "anos" ) List< FipeYear > year ) {

        return FipeModelDto.builder()
                           .model( model )
                           .year( year )
                           .build();

    }
}
