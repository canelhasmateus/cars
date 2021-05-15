package canelhas.cars.common.type;

import lombok.Builder;

@Builder( toBuilder = true )
public class CPF implements ValueType< String > {

    private final String value;

    @Override public String value( ) {
        return value;
    }

    public static CPF of( String value ) {
        return new CPF( value );
    }
}
