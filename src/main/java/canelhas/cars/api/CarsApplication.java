package canelhas.cars.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class CarsApplication {

    public static void main( String[] args ) {

        SpringApplication.run( CarsApplication.class, args );

        // TODO: 16/05/2021 friendly exception messages for unique constraints
        // TODO: 16/05/2021 Swagger Documentation.
        // TODO: 16/05/2021 check NotNull imports
        // TODO: 18/05/2021 centralize exceptionMessages
        // TODO: 18/05/2021 validation sort by smaller messages first.
        // TODO: 18/05/2021 some caching?
        // TODO: 18/05/2021 some concurrency? maybe find all three possible vehicle types.

    }


    @PostConstruct
    public void setTimeZone( ) {
        TimeZone.setDefault( TimeZone.getTimeZone( "GMT-3" ) );
    }


}
