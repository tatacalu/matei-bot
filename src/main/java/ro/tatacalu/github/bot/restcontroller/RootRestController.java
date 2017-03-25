package ro.tatacalu.github.bot.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Root rest controller mapped as the entry point of the web application.
 */
@RestController
public class RootRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RootRestController.class);

    private static final String GREETING_STRING_FORMAT = "This is matei-bot for Github, current UTC time: %s";

    @RequestMapping("/")
    public String index() {

        LOGGER.info("INFO  level - GET / command received");
        LOGGER.info("DEBUG level - GET / command received");

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        return String.format(GREETING_STRING_FORMAT, now.toString());
    }
}
