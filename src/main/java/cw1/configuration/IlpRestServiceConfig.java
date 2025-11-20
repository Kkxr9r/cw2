package cw1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class IlpRestServiceConfig {

    @Bean
    public String ilpBaseUrl() {
        String fromEnv = System.getenv("ILP_ENDPOINT");
        if (fromEnv == null || fromEnv.isBlank()) {
            return "https://ilp-rest-2025-bvh6e9hschfagrgy.ukwest-01.azurewebsites.net";
        }
        return fromEnv;
    }

    @Bean
    public org.springframework.web.client.RestTemplate restTemplate() {
        return new org.springframework.web.client.RestTemplate();
    }
}
