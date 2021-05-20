package canelhas.cars.common.utils;

import canelhas.cars.common.functional.Chain;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Function;

import static canelhas.cars.common.functional.Adjectives.partially;

public class AspectHelper {
    //region monorepo

    private AspectHelper( ) {}

    //endregion
    public static Method getMethod( JoinPoint joinPoint ) {
        return Chain.of( JoinPoint::getSignature )
                    .andThen( MethodSignature.class::cast )
                    .andThen( MethodSignature::getMethod ).apply( joinPoint );
    }

    public static < T extends Annotation > Function< Method, T > findAnnotation( Class< T > annotation ) {
        return partially( annotation, AnnotationUtils::findAnnotation );
    }
}
