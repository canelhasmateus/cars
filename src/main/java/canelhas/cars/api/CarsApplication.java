package canelhas.cars.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class CarsApplication {

    public static void main( String[] args ) {

        SpringApplication.run( CarsApplication.class, args );
    }


    @PostConstruct
    public void setTimeZone( ) {
        TimeZone.setDefault( TimeZone.getTimeZone( "GMT-3" ) );
    }

}
