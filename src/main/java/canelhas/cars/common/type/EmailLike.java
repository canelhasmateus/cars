package canelhas.cars.common.type;

import lombok.Builder;


@Builder( toBuilder = true )
public class EmailLike implements ValueType< String > {

    private final String value;

    public static < V, K > V of( K k ) {
        // TODO: 15/05/2021
        return null;
    }

    @Override public String value( ) {
        return value;
    }
}
