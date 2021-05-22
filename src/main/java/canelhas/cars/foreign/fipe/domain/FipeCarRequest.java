package canelhas.cars.foreign.fipe.domain;

import canelhas.cars.common.type.Responseable;
import canelhas.cars.foreign.fipe.csr.FipeClient;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.Optional;

import static java.lang.String.format;

@Builder( toBuilder = true )
@Getter
public class FipeCarRequest implements Responseable< FipeCarResponse > {

    private final FipeCar car;

    public static FipeCarRequest of( FipeCar car ) {

        return FipeCarRequest.builder()
                             .car( car )
                             .build();
    }

    @Override public HttpHeaders getHeaders( ) {
        return new HttpHeaders();
    }

    @Override public Optional< Serializable > getBody( ) {
        return Optional.empty();
    }

    @Override public String getUrl( ) {
        return FipeClient.BASE +
               format( "/carros/marcas/%s/modelos/%s/anos/%s",
                       car.getBrand().getCode(), car.getModel().getCode(), car.getYear().getCode() );
    }

    @Override public HttpMethod getMethod( ) {
        return HttpMethod.GET;
    }

    @Override public TypeReference< FipeCarResponse > getResponseType( ) {
        return new TypeReference<>() {};
    }
}
