package canelhas.cars.foreign.fipe.csr;

import java.util.ArrayList;
import java.util.List;

public class CollectionsHelper {

    //region monorepo

    private CollectionsHelper(){}
    //endregion
    public static < K > List< K > flatten( List< List< K > > nested ) {
        final List< K > flatenned = new ArrayList<>();
        nested.forEach( flatenned::addAll );
        return flatenned;
    }
}
