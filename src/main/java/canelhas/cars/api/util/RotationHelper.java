package canelhas.cars.api.util;

import canelhas.cars.api.vehicles.model.VehicleModel;
import canelhas.cars.api.vehicles.type.ModelYear;
import canelhas.cars.common.languaj.noun.Chain;
import canelhas.cars.common.utils.DateHelper;

import static canelhas.cars.common.languaj.Adjectives.hopefully;

public class RotationHelper {

    public static boolean rotatedOut( int currentWeekday, ModelYear year ) {
        var y = ModelYear.asInteger( year );
        return 1 >= Math.abs( y % 10 - 2 * ( currentWeekday % 7 - 2 ) );
//      A prova é trivial, e portanto, deixada a cargo do leitor.
    }

    public static Boolean isRotatedOut( VehicleModel vehicleModel ) {

        final int currentWeekDay = DateHelper.getCurrentWeekDay();
        final var getRotation = Chain.of( VehicleModel::typedYear )
                                     .andThen( year -> rotatedOut( currentWeekDay, year ) );

        return hopefully( getRotation )
                       .apply( vehicleModel )
                       .orElse( null );

    }

    //region monorepo

    private RotationHelper( ) {}

    //endregion

}
