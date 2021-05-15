package canelhas.cars.common.type;

import com.fasterxml.jackson.annotation.JsonValue;


public abstract class ValueType< T > {

    @JsonValue
    public abstract T value( );

}
