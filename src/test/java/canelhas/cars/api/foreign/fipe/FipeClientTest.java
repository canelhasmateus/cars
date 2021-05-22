package canelhas.cars.api.foreign.fipe;

import canelhas.cars.api.vehicles.crs.VehicleService;
import canelhas.cars.api.vehicles.domain.ModelBrand;
import canelhas.cars.api.vehicles.domain.ModelDto;
import canelhas.cars.api.vehicles.domain.ModelName;
import canelhas.cars.api.vehicles.domain.ModelYear;
import canelhas.cars.common.exception.ConflictException;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.exception.NotFoundException;
import canelhas.cars.foreign.fipe.csr.FipeBrandRequest;
import canelhas.cars.foreign.fipe.csr.FipeClient;
import canelhas.cars.foreign.fipe.csr.FipeModelRequest;
import canelhas.cars.foreign.fipe.domain.FipeBrand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class FipeClientTest {

    @Autowired RestTemplate template;

    //region brand
    @Test
    void givenTooVagueBrandThenThrows( ) {
        final var request = FipeBrandRequest.of( "a" );
        assertThrows( ConflictException.class, ( ) -> FipeClient.find( template, request ) );
    }

    @Test
    void givenInexactBrandThenThrows( ) {
        final var request = FipeBrandRequest.of( "lamborghin" );
        assertThrows( DomainException.class, ( ) -> FipeClient.find( template, request ) );
    }

    @Test
    void givenInexistingBrandThenThrows( ) {
        final var value = FipeBrandRequest.of( "definitivamente nao existe" );
        assertThrows( NotFoundException.class, ( ) -> FipeClient.find( template, value ) );
    }

    @Test
    void givenCaseDifferentBrandThenParses( ) {
        final var request = FipeBrandRequest.of( "toyota" );
        assertNotNull( FipeClient.find( template, request ) );
    }
    //endregion

    //region model
    @Test
    void givenTooVagueModelThenThrows( ) {

        final var brand     = FipeBrand.of( "a", 59 );
        final var modelYear = new ModelYear( "" );
        final var modelName = ModelName.of( "a" );
        final var request   = FipeModelRequest.of( brand, modelName, modelYear );

        assertThrows( ConflictException.class, ( ) -> FipeClient.find( template, request ) );
    }

    @Test
    void givenInexactModelThenThrows( ) {
        final var brand     = FipeBrand.of( "a", 59 );
        final var modelYear = new ModelYear( "" );
        final var modelName = ModelName.of( "AMAROK Highline CD 2.0 16V TDI 4x4 Die" );
        final var request   = FipeModelRequest.of( brand, modelName, modelYear );

        assertThrows( DomainException.class, ( ) -> FipeClient.find( template, request ) );
    }

    @Test
    void givenInexistingModelThenThrows( ) {

        final var brand     = FipeBrand.of( "a", 59 );
        final var modelYear = new ModelYear( "" );
        final var modelName = ModelName.of( "definitivamente nao existe" );
        final var request   = FipeModelRequest.of( brand, modelName, modelYear );

        assertThrows( NotFoundException.class, ( ) -> FipeClient.find( template, request ) );
    }


    @Test
    void givenCaseDifferentModelThenParses( ) {

        final var brand     = FipeBrand.of( "a", 59 );
        final var modelYear = new ModelYear( "2017 Gasolina" );
        final var modelName = ModelName.of( "AMAROK Highline CD 2.0 16V TDI 4x4 Dies.".toLowerCase() );
        final var request   = FipeModelRequest.of( brand, modelName, modelYear );
        assertNotNull( FipeClient.find( template, request ) );

    }
    //endregion

    @Test
    void givenValidThenParses( ) {

        final var dto = ModelDto.builder()
                                .brand( ModelBrand.of( "Lamborghini" ) )
                                .name( ModelName.of( "Gallardo Coupe Valentino Balboni LP550-2" ) )
                                .year( ModelYear.of( "2020 Gasolina" ) )
                                .build();

        assertNotNull( VehicleService.search( template, dto ) );

    }
}
