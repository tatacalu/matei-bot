package ro.tatacalu.github.bot.configuration;

import org.hibernate.validator.constraints.Length;
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

    @NotBlank(message = "The GitHub token must be provided")
    @Length(min = 40, max = 40, message = "The GitHub token length must be exactly 40 characters long")
    private String githubToken;


    public void setGithubToken(String githubToken) {
        this.githubToken = githubToken;
    }

    @Override
    public String getGithubToken() {
        return githubToken;
    }
}
