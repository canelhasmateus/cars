package canelhas.cars.api.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
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


    @Pointcut( "@annotation( canelhas.cars.api.queue.domain.Published)" )
    public void published( ) {}

    @AfterReturning( value = "published()", returning = "value" )
    public void publish( JoinPoint jp, Object value ) {
        publisher.publishEvent( value );
    }
}
