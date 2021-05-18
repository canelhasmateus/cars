package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.api.vehicles.domain.ModelBrand;
import canelhas.cars.api.vehicles.domain.ModelName;
import canelhas.cars.api.vehicles.domain.ModelYear;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.type.TypedId;
import canelhas.cars.foreign.fipe.domain.FipeBrand;
import canelhas.cars.foreign.fipe.domain.FipeModel;
import canelhas.cars.foreign.fipe.domain.FipeYear;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Function;

public class FipeClient {

    //region monorepo

    private FipeClient(){}
    //endregion

    //region fields
    private static final String BASE = "https://parallelum.com.br/fipe/api/v1";
    //endregion

    public static FipeBrand search( RestTemplate template, ModelBrand brand ) {

        //region definitions
        final ParameterizedTypeReference< List< FipeBrand > > responseType = new ParameterizedTypeReference<>() {};
        final Function< String, List< FipeBrand > >           doRequest    = successfully( getWith( template, responseType ) );
        final var                                             urls         = interpolate( brand );
        final var                                             value        = brand.value();
        //endregion
        return SearchHelper.genericSearch( doRequest, FipeBrand::getName, urls, value );
    }

    public static FipeModel search( RestTemplate template, ModelName name, TypedId< FipeBrand > brandId ) {
        //region definitions
        final ParameterizedTypeReference< List< FipeModel > > elementType = new ParameterizedTypeReference<>() {};
        final var                                             doRequest   = successfully( callGetWith( template, elementType ) );
        final var                                             urls        = interpolate( brandId );

        //endregion
        return SearchHelper.genericSearch( doRequest, FipeModel::getName, urls, name.value() );
    }

    public static FipeYear search( RestTemplate template, ModelYear year, TypedId< FipeBrand > brandId, TypedId< FipeModel > modelId ) {
        //region definitions
        final ParameterizedTypeReference< List< FipeYear > > elementType = new ParameterizedTypeReference<>() {};
        final var                                            doRequest   = successfully( callGetWith( template, elementType ) );
        final var                                            urls        = interpolate( brandId, modelId );
        //endregion
        return SearchHelper.genericSearch( doRequest, FipeYear::getName, urls, year.value() );
    }

    //region url
    private static String interpolate( TypedId< FipeBrand > brandId, TypedId< FipeModel > modelId ) {
        return BASE + "/carros/marcas/" + brandId.value() + "/modelos/" + modelId.value() + "/anos";
    }

    private static String interpolate( TypedId< FipeBrand > brandId ) {
        return BASE + "/carros/marcas/" + brandId.value() + "/modelos";
    }

    private static String interpolate( ModelBrand brand ) {
        return BASE + "/carros/marcas";
    }
    //endregion

    private static < T > Function< String, ResponseEntity< List< T > > > callGetWith( RestTemplate template, ParameterizedTypeReference< List< T > > bodyType ) {
        return s -> template.exchange( s, HttpMethod.GET, HttpEntity.EMPTY, bodyType );
    }

    private static < T > Function< String, ResponseEntity< List< T > > > getWith( RestTemplate template, ParameterizedTypeReference< List< T > > responseType ) {
        return s -> template.exchange( s, HttpMethod.GET, HttpEntity.EMPTY, responseType );
    }

    public static < K, V > Function< K, V > successfully( Function< K, ResponseEntity< V > > request ) {
        return ( K k ) -> {

            final var response = request.apply( k );

            if ( !String.valueOf( response.getStatusCodeValue() ).startsWith( "2" ) ) {
                // TODO: 18/05/2021 create exception
                throw new DomainException( "Um recurso dependente falhou. Tente novamente mais tarde",
                                           HttpStatus.BAD_GATEWAY );
            }

            return response.getBody();
        };
    }
    //endregion
}
