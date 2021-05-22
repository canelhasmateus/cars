package canelhas.cars.foreign.fipe.csr;

import canelhas.cars.api.vehicles.domain.ModelBrand;
import canelhas.cars.api.vehicles.domain.ModelName;
import canelhas.cars.api.vehicles.domain.ModelYear;
import canelhas.cars.foreign.fipe.domain.FipeBrand;
import canelhas.cars.foreign.fipe.domain.FipeModel;
import canelhas.cars.foreign.fipe.domain.FipeYear;
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

    public ModelBrand typedBrand( ) {
        return ModelBrand.of( this.getBrand().getName() );
    }

    public ModelName typedName( ) {
        return ModelName.of( this.getModel().getName() );
    }

    public ModelYear typedYear( ) {
        return ModelYear.of( this.getYear().getName() );
    }
}
