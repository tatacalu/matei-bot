package ro.tatacalu.github.bot.configuration;

/**
 * Interface used for accessing the configuration properties for the MateiBot application.
 */
public interface MateiBotConfigurationProperties {

    /**
     * Return the value of the sample Heroku configuration variable.
     */
    String getSampleHerokuConfigVar();
}
