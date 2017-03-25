package ro.tatacalu.github.bot.configuration;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * This class is a Spring bean that will hold the application configuration. The property values will be injected and validated during application start-up
 * and on the production environment will be read most likely from environment variables.
 */
@ConfigurationProperties(prefix = "mateibot")
@Component
@Validated
public class MateiBotConfigurationPropertiesImpl implements MateiBotConfigurationProperties {

    @NotBlank
    private String sampleHerokuConfigVar;

    public String getSampleHerokuConfigVar() {
        return sampleHerokuConfigVar;
    }

    public void setSampleHerokuConfigVar(String sampleHerokuConfigVar) {
        this.sampleHerokuConfigVar = sampleHerokuConfigVar;
    }
}
