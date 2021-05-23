package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.common.languaj.noun.Chain;
import canelhas.cars.common.utils.SearchHelper;
import canelhas.cars.foreign.fipe.domain.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Function;

import static canelhas.cars.common.utils.RequestHelper.request;
import static canelhas.cars.common.utils.RequestHelper.successfully;

public class FipeClient {

    public static final String BASE = "https://parallelum.com.br/fipe/api/v1";

    public static FipeBrand find( RestTemplate template, FipeBrandRequest requestEntity ) {
        //region definitions
        final Function< FipeBrandRequest, List< FipeBrand > > requester = successfully( req -> request( template, req ) );
        //endregion

        final var inputSearch    = requestEntity.getBrand().value();
        final var possibleBrands = requester.apply( requestEntity );
        return SearchHelper.findBest( inputSearch, possibleBrands );

    }

    public static FipeCar find( RestTemplate template, FipeModelRequest requestEntity ) {

        //region definitions
        Function< FipeModelRequest, FipeModelResponse > requester = successfully( req -> request( template, req ) );

        final var response  = requester.apply( requestEntity );
        final var bestBrand = requestEntity.getBrand();
        //endregion

        final var inputModel     = requestEntity.getName().value();
        final var possibleModels = response.getModels();
        final var bestModel      = SearchHelper.findBest( inputModel, possibleModels );

        final var inputYear     = requestEntity.getYear().value();
        final var possibleYears = response.getYears();
        final var bestYear      = SearchHelper.findBest( inputYear, possibleYears );

        return FipeCar.builder()
                      .brand( bestBrand )
                      .model( bestModel )
                      .year( bestYear )
                      .build();
    }

    public static FipeCar find( RestTemplate template, FipeCarRequest requestEntity ) {
        //region definitions
        final Function< FipeCarRequest, FipeCarResponse > doRequest = successfully( req -> request( template, req ) );
        //endregion

        return Chain.of( doRequest )
                    .andThen( FipeCar::of )
                    .apply( requestEntity );
    }

    //region monorepo
    private FipeClient( ) {}
    //endregion

}
