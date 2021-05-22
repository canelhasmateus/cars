package canelhas.cars.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableAsync
public class CarsApplication {

    public static void main( String[] args ) {

        SpringApplication.run( CarsApplication.class, args );

        // TODO: 16/05/2021 friendly exception messages for unique constraints
        // TODO: 16/05/2021 Swagger Documentation.
        // TODO: 18/05/2021 some caching at fipe api calls?


    }


    @PostConstruct
    public void setTimeZone( ) {
        TimeZone.setDefault( TimeZone.getTimeZone( "GMT-3" ) );
    }


}
