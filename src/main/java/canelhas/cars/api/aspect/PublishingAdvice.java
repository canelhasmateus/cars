package canelhas.cars.api.aspect;

import canelhas.cars.api.queue.domain.Published;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PublishingAdvice {

    private final ApplicationEventPublisher publisher;

    @Published
    @Pointcut( "@annotation( canelhas.cars.api.queue.domain.Published)" )
    public void published( ) {}

    @AfterReturning( value = "published()", returning = "value" )
    public < T > void publish( T value ) {
        publisher.publishEvent( value );
    }
}
