package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.common.utils.RequestHelper;
import canelhas.cars.foreign.fipe.domain.FipeBrand;
import canelhas.cars.foreign.fipe.domain.FipeModel;
import canelhas.cars.foreign.fipe.domain.FipeYear;
import org.springframework.web.client.RestTemplate;

import static canelhas.cars.common.functional.Adjectives.collectively;
import static canelhas.cars.common.functional.Adjectives.partially;
import static canelhas.cars.common.utils.RequestHelper.successfully;

public class FipeClient {

    public static final String BASE = "https://parallelum.com.br/fipe/api/v1";

    public static FipeBrand find( RestTemplate template, @LRU FipeBrandRequest request ) {

        //region definitions
        final var requester = new RequestHelper( template );
        final var brandList = successfully( __ -> requester.doRequest( request ) )
                                      .apply( request );
        //endregion

        //region find best candidate
        final var search        = request.getBrand().value();
        final var findBestIndex = partially( SearchHelper::bestIndex, search );
        final var bestIndex = collectively( FipeBrand::getName )
                                      .andThen( findBestIndex )
                                      .apply( brandList );
        //endregion

        return brandList.get( bestIndex );
    }
    //endregion

    public static FipeCar find( RestTemplate template, @LRU FipeModelRequest request ) {

        //region response
        final var brand     = request.getBrand();
        final var requester = new RequestHelper( template );
        FipeModelResponse response = successfully( __ -> requester.doRequest( request ) )
                                             .apply( request );
        //endregion

        final var searchedModel  = request.getName().value();
        final var models         = response.getModels();
        final var modelNameList  = collectively( FipeModel::getName ).apply( models );
        final var bestModelIndex = SearchHelper.bestIndex( searchedModel, modelNameList );
        final var bestModel      = models.get( bestModelIndex );

        final var searchedYear  = request.getYear().value();
        final var years         = response.getYears();
        final var yearNameList  = collectively( FipeYear::getName ).apply( years );
        final var bestYearIndex = SearchHelper.bestIndex( searchedYear, yearNameList );
        final var bestYear      = years.get( bestYearIndex );

        return FipeCar.builder()
                      .brand( brand )
                      .year( bestYear )
                      .model( bestModel )
                      .build();
    }
    //region url


}
