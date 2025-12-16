package ma.enset.conferenceservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 1. Récupérer le contexte de la requête HTTP actuelle
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attributes != null) {
                    // 2. Récupérer le header Authorization (Bearer eyJhbG...)
                    String authHeader = attributes.getRequest().getHeader("Authorization");

                    // 3. L'injecter dans la requête Feign sortante
                    if (authHeader != null) {
                        template.header("Authorization", authHeader);
                    }
                }
            }
        };
    }
}