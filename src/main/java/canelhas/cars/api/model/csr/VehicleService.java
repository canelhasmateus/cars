package canelhas.cars.api.model.csr;

import canelhas.cars.api.model.domain.VehicleDto;
import canelhas.cars.api.model.model.Vehicle;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Flux;
import canelhas.cars.common.type.TypedId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

@Service
public class VehicleService {



    @Transactional
    public Vehicle register( VehicleDto request ) {

        UnaryOperator< Vehicle > searchFromApi = v -> v;

        return Flux.of( VehicleDto::toEntity )
                   .andThen( searchFromApi )
                   .andThen( vehicleRepository::save )
                   .apply( request );

    }

    public List< Vehicle > read( TypedId< User > id ) {
        return Collections.emptyList();
    }


}
