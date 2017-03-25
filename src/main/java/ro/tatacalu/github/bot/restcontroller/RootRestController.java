package ro.tatacalu.github.bot.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.tatacalu.github.bot.configuration.MateiBotConfigurationProperties;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Root rest controller mapped as the entry point of the web application.
 */
@RestController
public class RootRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootRestController.class);

    private static final String GREETING_STRING_FORMAT = "This is matei-bot for Github, current UTC time: %s";

    @Autowired
    private MateiBotConfigurationProperties configurationProperties;

    @RequestMapping("/")
    public String index() {

        LOGGER.info("INFO  level - GET / command received; sample config var value: [{}]", configurationProperties.getSampleHerokuConfigVar());

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        return String.format(GREETING_STRING_FORMAT, now.toString());
    }
}
