package canelhas.cars.common.type;

import lombok.Builder;

import java.util.Date;


@Builder( toBuilder = true )
public class MajorityDate implements ValueType< Date > {

    private final Date value;

    public static < V, K > V of( K k ) {
        return null;
    }

    @Override public Date value( ) {

        return value;
    }
}
