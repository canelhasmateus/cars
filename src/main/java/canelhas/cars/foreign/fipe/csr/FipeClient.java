package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.common.utils.RequestHelper;
import canelhas.cars.foreign.fipe.domain.*;
import org.springframework.web.client.RestTemplate;

import static canelhas.cars.common.languaj.Adjectives.collectively;
import static canelhas.cars.common.utils.RequestHelper.successfully;
import static canelhas.cars.common.utils.SearchHelper.bestIndex;

public class FipeClient {

    public static final String BASE = "https://parallelum.com.br/fipe/api/v1";


    // TODO: 22/05/2021 Cache these three requests.
    public static FipeBrand find( RestTemplate template, FipeBrandRequest request ) {

        //region definitions
        final var requester = new RequestHelper( template );
        final var brandList = successfully( __ -> requester.doRequest( request ) )
                                      .apply( request );
        //endregion

        //region bestBrand
        final var search = request.getBrand().value();
        final var bestIndex = collectively( FipeBrand::getName )
                                      .andThen( s -> bestIndex( search, s ) )
                                      .apply( brandList );
        //endregion

        return brandList.get( bestIndex );
    }

    public static FipeCar find( RestTemplate template, FipeModelRequest request ) {

        //region response
        final var bestBrand = request.getBrand();
        final var requester = new RequestHelper( template );
        final var response = successfully( __ -> requester.doRequest( request ) )
                                     .apply( request );
        //endregion

        //region bestModel
        final var searchedModel = request.getName().value();
        final var modelList     = response.getModels();
        final var bestModelIndex = collectively( FipeModel::getName )
                                           .andThen( s -> bestIndex( searchedModel, s ) )
                                           .apply( modelList );
        final var bestModel = modelList.get( bestModelIndex );
        //endregion

        //region bestYear
        final var searchedYear = request.getYear().value();
        final var yearList     = response.getYears();
        final var bestYearIndex = collectively( FipeYear::getName )
                                          .andThen( s -> bestIndex( searchedYear, s ) )
                                          .apply( yearList );
        final var bestYear = yearList.get( bestYearIndex );
        //endregion

        return FipeCar.builder()
                      .brand( bestBrand )
                      .model( bestModel )
                      .year( bestYear )
                      .build();
    }

    public static FipeCar find( RestTemplate template, FipeCarRequest request ) {

        //region response
        final var requester = new RequestHelper( template );
        final var response = successfully( __ -> requester.doRequest( request ) )
                                     .apply( request );
        //endregion

        return FipeCar.of( response );
    }


    //region monorepo
    private FipeClient( ) {}
    //endregion

}
