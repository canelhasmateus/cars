package canelhas.cars.api.vehicles.domain;

import canelhas.cars.api.user.domain.UserDto;
import canelhas.cars.api.vehicles.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder( toBuilder = true )
@Getter
@AllArgsConstructor
public class VehicleDto {

    private final UserDto  userId;
    private final ModelDto model;


    public static Vehicle toEntity( ModelDto modelDto ) {
        return null;
    }

    public static VehicleDto fromEntity( Vehicle vehicle ) {
        return null;
    }
}
