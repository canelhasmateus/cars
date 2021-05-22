package canelhas.cars.foreign.fipe.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder( toBuilder = true )
@Getter
public class FipeCarResponse {
    //region fields
    private final String  value;
    private final String  brand;
    private final String  model;
    private final Integer year;
    private final String  fuel;
    private final String  fipeCode;
    private final String  month;
    private final Integer type;
    private final String  fuelType;

    //endregion
    @JsonCreator( mode = JsonCreator.Mode.PROPERTIES )
    public static FipeCarResponse of( @JsonProperty( VALOR ) String value,
                                      @JsonProperty( MARCA ) String brand,
                                      @JsonProperty( MODELO ) String model,
                                      @JsonProperty( ANO_MODELO ) Integer year,
                                      @JsonProperty( COMBUSTIVEL ) String fuel,
                                      @JsonProperty( CODIGO_FIPE ) String fipeCode,
                                      @JsonProperty( MES_REFERENCIA ) String month,
                                      @JsonProperty( TIPO_VEICULO ) Integer type,
                                      @JsonProperty( SIGLA_COMBUSTIVEL ) String fuelType
                                    ) {


        return FipeCarResponse.builder()
                              .value( value )
                              .brand( brand )
                              .model( model )
                              .year( year )
                              .fuel( fuel )
                              .fipeCode( fipeCode )
                              .month( month )
                              .type( type )
                              .fuelType( fuelType )
                              .build();
    }

    //region keys
    public static final String VALOR             = "Valor";
    public static final String MARCA             = "Marca";
    public static final String MODELO            = "Modelo";
    public static final String ANO_MODELO        = "AnoModelo";
    public static final String COMBUSTIVEL       = "Combustivel";
    public static final String CODIGO_FIPE       = "CodigoFipe";
    public static final String MES_REFERENCIA    = "MesReferencia";
    public static final String TIPO_VEICULO      = "TipoVeiculo";
    public static final String SIGLA_COMBUSTIVEL = "SiglaCombustivel";
    //endregion

}
