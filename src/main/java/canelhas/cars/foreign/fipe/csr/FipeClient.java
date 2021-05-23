package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.foreign.fipe.domain.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Function;

import static canelhas.cars.common.languaj.Adjectives.collectively;
import static canelhas.cars.common.utils.RequestHelper.request;
import static canelhas.cars.common.utils.RequestHelper.successfully;
import static canelhas.cars.common.utils.SearchHelper.bestIndex;

public class FipeClient {

    public static final String BASE = "https://parallelum.com.br/fipe/api/v1";

    public static FipeBrand find( RestTemplate template, FipeBrandRequest requestEntity ) {

        //region definitions
        Function< FipeBrandRequest, List< FipeBrand > > doRequest = successfully( req -> request( template, req ) );
        //endregion

        //region implementation
        final var search         = requestEntity.getBrand().value();
        final var possibleBrands = doRequest.apply( requestEntity );


        final var bestIndex = collectively( FipeBrand::getName )
                                      .andThen( candidates -> bestIndex( search, candidates ) )
                                      .apply( possibleBrands );
        //endregion

        return possibleBrands.get( bestIndex );
    }

    public static FipeCar find( RestTemplate template, FipeModelRequest requestEntity ) {

        //region definitions
        Function< FipeModelRequest, FipeModelResponse > doRequest = successfully( req -> request( template, req ) );

        final var response       = doRequest.apply( requestEntity );
        final var model          = requestEntity.getName().value();
        final var possibleModels = response.getModels();
        final var year           = requestEntity.getYear().value();
        final var possibleYears  = response.getYears();
        //endregion

        //region bestModel
        final var bestModelIndex = collectively( FipeModel::getName )
                                           .andThen( candidates -> bestIndex( model, candidates ) )
                                           .apply( possibleModels );
        final var bestModel = possibleModels.get( bestModelIndex );
        //endregion

        //region bestYear
        final var bestYearIndex = collectively( FipeYear::getName )
                                          .andThen( candidates -> bestIndex( year, candidates ) )
                                          .apply( possibleYears );
        final var bestYear = possibleYears.get( bestYearIndex );
        //endregion

        return FipeCar.builder()
                      .brand( requestEntity.getBrand() )
                      .model( bestModel )
                      .year( bestYear )
                      .build();
    }

    public static FipeCar find( RestTemplate template, FipeCarRequest requestEntity ) {

        //region response
        Function< FipeCarRequest, FipeCarResponse > doRequest = successfully( req -> request( template, req ) );
        //endregion

        final var response = doRequest.apply( requestEntity );
        return FipeCar.of( response );
    }

    //region monorepo
    private FipeClient( ) {}
    //endregion

}
