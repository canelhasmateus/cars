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

    }

    @PostConstruct
    public void setTimeZone( ) {
        TimeZone.setDefault( TimeZone.getTimeZone( "GMT-3" ) );
    }

}
