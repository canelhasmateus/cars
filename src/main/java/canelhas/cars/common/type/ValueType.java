package canelhas.cars.common.type;

import com.fasterxml.jackson.annotation.JsonValue;


public interface  ValueType< T > {

    @JsonValue
    public  T value( );

}
