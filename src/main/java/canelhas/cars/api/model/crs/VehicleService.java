package canelhas.cars.api.model.crs;

import canelhas.cars.api.model.domain.VehicleDto;
import canelhas.cars.api.model.model.Vehicle;
import canelhas.cars.api.user.model.User;
import canelhas.cars.common.functional.Chain;
import canelhas.cars.common.type.TypedId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class VehicleService {


    private final VehicleRepository vehicleRepository;

    @Transactional
    public Vehicle register( VehicleDto request ) {

        UnaryOperator< Vehicle > searchFromApi = v -> v;

        return Chain.of( VehicleDto::toEntity )
                    .andThen( searchFromApi )
                    .andThen( vehicleRepository::save )
                    .apply( request );

    }

    public List< Vehicle > read( TypedId< User > id ) {
        return Collections.emptyList();
    }


}
