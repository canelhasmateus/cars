package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.api.vehicles.domain.ModelBrand;
import canelhas.cars.common.interfaces.Responseable;
import canelhas.cars.foreign.fipe.domain.FipeBrand;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.List;


@Builder( toBuilder = true )
@Getter
@Value
public class FipeBrandRequest implements Responseable< List< FipeBrand > > {

    private final ModelBrand brand;

    public static FipeBrandRequest of( ModelBrand brand ) {

        return FipeBrandRequest.builder()
                               .brand( brand )
                               .build();
    }

    public static FipeBrandRequest of( String brand ) {
        return FipeBrandRequest.builder()
                               .brand( ModelBrand.of( brand ) )
                               .build();
    }

    @Override public HttpHeaders getHeaders( ) {
        return new HttpHeaders();
    }

    @Override public JSONObject getBody( ) {
        return new JSONObject();
    }

    @Override public String getUrl( ) {
        return FipeClient.BASE + "/carros/marcas";
    }

    @Override public HttpMethod getMethod( ) {
        return HttpMethod.GET;
    }

    @Override public TypeReference< List< FipeBrand > > getResponseType( ) {
        return new TypeReference<>() {};
    }
}
