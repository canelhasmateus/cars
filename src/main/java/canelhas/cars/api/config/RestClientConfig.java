package canelhas.cars.api.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Configuration
public class RestClientConfig {

    @Bean
    @Scope( "prototype" )
    public RestTemplate restTemplate( RestTemplateBuilder restTemplateBuilder ) {
        var restTemplate = restTemplateBuilder.errorHandler( new ResponseErrorHandler() {
            @Override
            public boolean hasError( ClientHttpResponse clientHttpResponse ) {
                return false;
            }

            @Override
            public void handleError( ClientHttpResponse clientHttpResponse ) {
                //refused bequest. whatever. Shamelessly copied from the internet
            }
        } ).setConnectTimeout( Duration.ofSeconds( 3 ) ).setReadTimeout( Duration.ofSeconds( 10 ) ).build();


        restTemplate.setInterceptors( Collections.emptyList() );

        var requestFactory = new SimpleClientHttpRequestFactory();
        restTemplate.setRequestFactory( new BufferingClientHttpRequestFactory( requestFactory ) );

        return restTemplate;
    }
}