package canelhas.cars.api.foreign.fipe;

import canelhas.cars.api.vehicles.domain.ModelBrand;
import canelhas.cars.common.exception.ConflictException;
import canelhas.cars.common.exception.DomainException;
import canelhas.cars.common.exception.NotFoundException;
import canelhas.cars.foreign.fipe.csr.FipeClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class FipeClientTest {

    @Autowired RestTemplate template;

    @Test
    void givenTooVagueThenThrows( ) {

        assertThrows( ConflictException.class, ( ) -> FipeClient.search( template, ModelBrand.of( "a" ) ) );

    }

    @Test
    void givenInexactThenThrows( ) {
        assertThrows( DomainException.class, ( ) -> FipeClient.search( template, ModelBrand.of( "lamborghin" ) ) );
    }

    @Test
    void givenInextistingThenThrows( ) {
        assertThrows( NotFoundException.class, ( ) -> FipeClient.search( template, ModelBrand.of( "definitivamente nao existe" ) ) );
    }

    @Test
    void givenCaseDifferentThenParses( ) {
        assertNotNull( FipeClient.search( template, ModelBrand.of( "toyota" ) ) );
    }


}
