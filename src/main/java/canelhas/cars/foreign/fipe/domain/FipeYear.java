package canelhas.cars.foreign.fipe.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder( toBuilder = true )
@Value
public class FipeYear {


    private final String name;
    private final String code;

    //region constructor
    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static FipeYear of( @JsonProperty( "nome" ) String name,
                               @JsonProperty( "codigo" ) String code ) {

        return FipeYear.builder()
                       .name( name )
                       .code( code )
                       .build();
    }
}
//endregions