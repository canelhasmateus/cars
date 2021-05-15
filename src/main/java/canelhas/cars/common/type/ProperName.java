package canelhas.cars.common.type;

import lombok.Builder;

@Builder( toBuilder = true )
public class ProperName implements ValueType< String > {

    private final String value;

    public static < V, K > V of( K k ) {
        return null;
    }


    @Override public String value( ) {
        return value;
    }
}
