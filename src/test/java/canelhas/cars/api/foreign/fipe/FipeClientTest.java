package canelhas.cars.api.foreign.fipe;

import canelhas.cars.api.vehicles.domain.ModelBrand;
import canelhas.cars.api.vehicles.domain.ModelDto;
import canelhas.cars.api.vehicles.domain.ModelName;
import canelhas.cars.api.vehicles.domain.ModelYear;
import canelhas.cars.common.exception.ConflictException;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.exception.NotFoundException;
import canelhas.cars.common.type.TypedId;
import canelhas.cars.foreign.fipe.csr.FipeClient;
import canelhas.cars.foreign.fipe.domain.FipeBrand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class FipeClientTest {

    @Autowired RestTemplate template;

    //region brand
    @Test
    void givenTooVagueBrandThenThrows( ) {
        final var value = ModelBrand.of( "a" );
        assertThrows( ConflictException.class, ( ) -> FipeClient.search( value, template ) );
    }

    @Test
    void givenInexactBrandThenThrows( ) {
        final var value = ModelBrand.of( "lamborghin" );
        assertThrows( DomainException.class, ( ) -> FipeClient.search( value, template ) );
    }

    @Test
    void givenInexistingBrandThenThrows( ) {
        final var value = ModelBrand.of( "definitivamente nao existe" );
        assertThrows( NotFoundException.class, ( ) -> FipeClient.search( value, template ) );
    }

    @Test
    void givenCaseDifferentBrandThenParses( ) {
        final var value = ModelBrand.of( "toyota" );
        assertNotNull( FipeClient.search( value, template ) );
    }
    //endregion

    //region model
    @Test
    void givenTooVagueModelThenThrows( ) {
        final var modelYear = new ModelYear( "" );
        final var brandId   = TypedId.of( FipeBrand.class, 59 );
        final var modelName = ModelName.of( "a" );
        assertThrows( ConflictException.class, ( ) -> FipeClient.search( modelName, modelYear, brandId, template ) );
    }

    @Test
    void givenInexactModelThenThrows( ) {
        final var modelYear = new ModelYear( "" );
        final var brandId   = TypedId.of( FipeBrand.class, 59 );
        final var modelName = ModelName.of( "AMAROK Highline CD 2.0 16V TDI 4x4 Die" );
        assertThrows( DomainException.class, ( ) -> FipeClient.search( modelName, modelYear, brandId, template ) );
    }

    @Test
    void givenInexistingModelThenThrows( ) {
        final var modelYear = new ModelYear( "" );
        final var brandId   = TypedId.of( FipeBrand.class, 59 );
        final var modelName = ModelName.of( "definitivamente nao existe" );

        assertThrows( NotFoundException.class, ( ) -> FipeClient.search( modelName, modelYear, brandId, template ) );
    }


    @Test
    void givenCaseDifferentModelThenParses( ) {

        final var modelYear = new ModelYear( "2017 Gasolina" );
        final var brandId   = TypedId.of( FipeBrand.class, 59 );
        final var modelName = ModelName.of( "AMAROK Highline CD 2.0 16V TDI 4x4 Dies.".toLowerCase() );
        assertNotNull( FipeClient.search( modelName, modelYear, brandId, template ) );

    }
    //endregion

    @Test
    void givenValidThenParses( ) {

        final var dto = ModelDto.builder()
                                .brand( ModelBrand.of( "Lamborghini" ) )
                                .name( ModelName.of( "Gallardo Coupe Valentino Balboni LP550-2" ) )
                                .year( ModelYear.of( "2020 Gasolina" ) )
                                .build();

        assertNotNull( FipeClient.search( template, dto ) );

    }
}
