package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.api.vehicles.domain.ModelName;
import canelhas.cars.api.vehicles.domain.ModelYear;
import canelhas.cars.api.vehicles.model.VehicleModel;
import canelhas.cars.common.interfaces.Responseable;
import canelhas.cars.foreign.fipe.domain.FipeBrand;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static java.lang.String.format;

@Builder( toBuilder = true )
@Getter
public class FipeModelRequest implements Responseable< FipeModelResponse > {

    private final ModelYear year;
    private final ModelName name;

    private final FipeBrand brand;


    @Override
    public HttpHeaders getHeaders( ) {
        return new HttpHeaders();
    }

    @Override public JSONObject getBody( ) {
        return new JSONObject();
    }

    @Override public String getUrl( ) {
        return format( FipeClient.BASE + "/carros/marcas/%s/modelos", brand.getCode() );
    }

    @Override public HttpMethod getMethod( ) {
        return HttpMethod.GET;
    }

    @Override public TypeReference< FipeModelResponse > getResponseType( ) {
        return new TypeReference<>() {};
    }

    public static FipeModelRequest of( FipeBrand brand, ModelName name, ModelYear year ) {
        return FipeModelRequest.builder()
                               .brand( brand )
                               .name( name )
                               .year( year )
                               .build();
    }
}
