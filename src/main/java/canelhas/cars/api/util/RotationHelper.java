package canelhas.cars.api.util;

import canelhas.cars.api.vehicles.model.VehicleModel;
import canelhas.cars.api.vehicles.type.ModelYear;
import canelhas.cars.common.languaj.noun.Chain;
import canelhas.cars.common.utils.DateHelper;

import static canelhas.cars.common.languaj.Adjectives.hopefully;

public class RotationHelper {
    /**
     * A prova é trivial, e portanto, deixada a cargo do leitor.
     *  <br> ( Criatividade, a gente se vê por aqui )
     * **/
    public static boolean rotatedOut( int currentWeekday, ModelYear year ) {
        var y = ModelYear.asInteger( year );
        return Math.abs( y % 10 - 2 * ( currentWeekday % 7 - 2 ) ) <= 1;
    }


    public static Boolean isRotatedOut( VehicleModel vehicleModel ) {

        //region definitions
        final int currentWeekDay = DateHelper.getCurrentWeekDay();
        final var getRotation = Chain.of( VehicleModel::typedYear )
                                     .andThen( year -> rotatedOut( currentWeekDay, year ) );
        //endregion

        return hopefully( getRotation )
                       .apply( vehicleModel )
                       .orElse( null );

    }

    //region monorepo

    private RotationHelper( ) {}

    //endregion

}
