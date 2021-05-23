package canelhas.cars.api.queue.crs;

import canelhas.cars.api.user.model.User;
import canelhas.cars.api.vehicles.model.Vehicle;
import canelhas.cars.common.type.Insertion;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    @EventListener
    public void sendWelcomeEmail( Insertion< User > newUser ) {

        System.out.printf( " A fazer: Disparar email de boas vindas para %s",
                           newUser.getEntity().getEmail() );
    }

    @EventListener
    public void sendConfirmationEmail( Insertion< Vehicle > newVehicle ) {

        System.out.printf( " A fazer: Disparar email de confirmação para %s",
                           newVehicle.getEntity().getOwner().getEmail() );
    }

}
