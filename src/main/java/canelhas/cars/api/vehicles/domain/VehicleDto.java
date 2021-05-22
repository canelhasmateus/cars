package canelhas.cars.api.vehicles.domain;

import canelhas.cars.api.user.domain.UserDto;
import canelhas.cars.api.user.model.User;
import canelhas.cars.api.vehicles.model.Vehicle;
import canelhas.cars.common.type.Insertion;
import canelhas.cars.common.type.TypedId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder( toBuilder = true )
@Value
@AllArgsConstructor
public class VehicleDto {

    private final UserDto  owner;
    private final ModelDto model;

    public static Vehicle asEntity( TypedId< User > userId, ModelDto modelDto ) {

        final var owner = User.of( userId );
        final var model = ModelDto.asEntity( modelDto );

        return Vehicle.builder()
                      .model( model )
                      .owner( owner )
                      .build();
    }

    public static VehicleDto of( Vehicle vehicle ) {

        final var owner = UserDto.of( vehicle.getOwner() );
        final var model = ModelDto.of( vehicle.getModel() );

        return VehicleDto.builder()
                         .owner( owner )
                         .model( model )
                         .build();
    }

    public static VehicleDto of( Insertion< Vehicle > insertion ) {
        return VehicleDto.of( insertion.getEntity() );
    }


}
