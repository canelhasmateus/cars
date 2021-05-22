package canelhas.cars.api.queue.crs;

import canelhas.cars.api.user.model.User;
import canelhas.cars.api.vehicles.crs.Insertion;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class QueueService {

    @Async
    @EventListener
    public void sendWelcomeEmail( Insertion< User > newUser ) {

    }
}
