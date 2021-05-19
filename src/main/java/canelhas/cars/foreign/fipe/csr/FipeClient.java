package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.api.vehicles.domain.ModelBrand;
import canelhas.cars.api.vehicles.domain.ModelDto;
import canelhas.cars.api.vehicles.domain.ModelName;
import canelhas.cars.api.vehicles.domain.ModelYear;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.type.TypedId;
import canelhas.cars.foreign.fipe.domain.FipeBrand;
import canelhas.cars.foreign.fipe.domain.FipeModel;
import canelhas.cars.foreign.fipe.domain.FipeModelDto;
import canelhas.cars.foreign.fipe.domain.FipeYear;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Function;

import static canelhas.cars.common.functional.Adjectives.collectively;

public class FipeClient {

    //region monorepo

    private FipeClient( ) {}
    //endregion

    //region fields
    private static final String BASE = "https://parallelum.com.br/fipe/api/v1";
    //endregion

    public static ModelDto search( RestTemplate template, ModelDto dto ) {

        //region definitions
        final var inputBrand = dto.getBrand();
        final var inputYear  = dto.getYear();
        final var inputName  = dto.getName();

        final var                  fipeBrand   = search( inputBrand, template );
        final TypedId< FipeBrand > brandId     = TypedId.of( fipeBrand.getCode() );
        final var                  nameAndYear = search( inputName, inputYear, brandId, template );

        final var outputBrand = ModelBrand.of( fipeBrand.getName() );
        final var outputName  = ModelName.of( nameAndYear.getFirst().getName() );
        final var outputYear  = ModelYear.of( nameAndYear.getSecond().getName() );
        //endregion

        return ModelDto.builder()
                       .brand( outputBrand )
                       .name( outputName )
                       .year( outputYear )
                       .build();
    }

    public static FipeBrand search( ModelBrand brand, RestTemplate template ) {

        //region definitions
        final var responseType = new ParameterizedTypeReference< List< FipeBrand > >() {};
        final var urls         = interpolate( brand );
        //endregion

        final var response   = successfully( getWith( template, responseType ) ).apply( urls );
        final var searchList = collectively( FipeBrand::getName ).apply( response );
        final var bestIndex  = SearchHelper.bestIndex( brand.value(), searchList );

        return response.get( bestIndex );
    }

    public static Pair< FipeModel, FipeYear > search( ModelName name, ModelYear year, TypedId< FipeBrand > brandId, RestTemplate template ) {
        //region definitions
        final var elementType = new ParameterizedTypeReference< FipeModelDto >() {};
        final var urls        = interpolate( brandId );
        final var response    = successfully( getObjectWith( template, elementType ) ).apply( urls );
        //endregion

        final var models         = response.getModel();
        final var modelNameList  = collectively( FipeModel::getName ).apply( models );
        final var bestModelIndex = SearchHelper.bestIndex( name.value(), modelNameList );
        final var bestModel      = models.get( bestModelIndex );

        final var years         = response.getYear();
        final var yearNameList  = collectively( FipeYear::getName ).apply( years );
        final var bestYearIndex = SearchHelper.bestIndex( year.value(), yearNameList );
        final var bestYear      = years.get( bestYearIndex );

        return Pair.of( bestModel, bestYear );
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

    private static < T > Function< String, ResponseEntity< T > > getObjectWith( RestTemplate template, ParameterizedTypeReference< T > bodyType ) {
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
