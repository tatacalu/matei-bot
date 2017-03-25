package ro.tatacalu.github.bot.restcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * Root rest controller mapped as the entry point of the web application.
 */
@RestController
public class RootRestController {

    private static final String GREETING_STRING_FORMAT = "This is matei-bot for Github, current UTC time: %s";

    @RequestMapping("/")
    public String index() {

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        return String.format(GREETING_STRING_FORMAT, now.toString());
    }
}
