package canelhas.cars.common.type;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;


@SuppressWarnings( "EqualsWhichDoesntCheckParameterClass" )
public abstract class ValueType< T > implements Serializable {

    @JsonValue public abstract T value( );

    @Override
    public String toString( ) {
        return value().toString();
    }

    public int hashCode( ) {
        return value().hashCode();
    }

    public boolean equals( Object other ) {
        return value().equals( other );
    }
}
