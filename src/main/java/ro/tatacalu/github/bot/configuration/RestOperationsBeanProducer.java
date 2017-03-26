package ro.tatacalu.github.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Spring {@link Configuration} class that produces {@link RestTemplate} instances.
 */
@Configuration
public class RestOperationsBeanProducer {

    @Bean("githubClientRestTemplate")
    public RestOperations createRestTemplate() {
        return new RestTemplate();
    }
}
