package canelhas.cars.foreign.fipe.domain;

import canelhas.cars.common.type.Nameable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder( toBuilder = true )
@Value
public class FipeBrand implements Nameable {

    private final String  name;
    private final Integer code;

    //region constructor
    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static FipeBrand of( @JsonProperty( "nome" ) String name,
                                @JsonProperty( "codigo" ) Integer code ) {

        return FipeBrand.builder()
                        .name( name )
                        .code( code )
                        .build();
    }

    public static FipeBrand of( String name ) {
        return FipeBrand.builder()
                        .name( name )
                        .build();
    }


    //endregion
}
