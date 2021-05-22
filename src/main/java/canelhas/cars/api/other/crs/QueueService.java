package canelhas.cars.api.other.crs;

import canelhas.cars.api.user.model.User;
import canelhas.cars.api.vehicles.crs.Insertion;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class QueueService {

    @EventListener
    public void sendWelcomeEmail( Insertion< User > newUser ) {

        System.out.println( format( "Todo: Disparar email para %s",
                                    newUser.getEntity().getEmail() ) );
    }
}
