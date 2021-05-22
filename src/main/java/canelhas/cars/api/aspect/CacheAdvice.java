package canelhas.cars.api.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheAdvice {

    @Before( "execution(public * *.*(.., @canelhas.cars.api.other.domain.CacheKey ( * ),..))" )
    public void lookupCache( JoinPoint joinPoint ) {
        // TODO: 22/05/2021
    }
}
