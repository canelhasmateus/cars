package canelhas.cars.foreign.fipe.domain;

import canelhas.cars.common.type.Price;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder( toBuilder = true )
@Getter
@Value
public class FipeCar {

    private final FipeBrand brand;
    private final FipeModel model;
    private final FipeYear  year;
    private final Price     price;

    public static FipeCar of( FipeCarResponse response ) {
        final var brand = FipeBrand.of( response.getBrand() );
        final var model = FipeModel.of( response.getModel() );
        final var year  = FipeYear.of( response.getYear() );
        final var price = Price.of( response.getValue() );
        return FipeCar.builder()
                      .brand( brand )
                      .year( year )
                      .model( model )
                      .price( price )
                      .build();
    }

}
