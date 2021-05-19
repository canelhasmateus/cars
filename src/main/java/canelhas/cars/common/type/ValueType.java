package canelhas.cars.common.type;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;


public abstract class ValueType< T > implements Serializable {


    @JsonValue abstract T value( );

    @Override
    public String toString( ) {
        return value().toString();
    }

}
