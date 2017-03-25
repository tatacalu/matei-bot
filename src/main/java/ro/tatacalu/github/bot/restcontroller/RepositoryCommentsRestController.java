package ro.tatacalu.github.bot.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller called by a GitHub repository webhook.
 */
@RestController
public class RepositoryCommentsRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryCommentsRestController.class);

    @PostMapping(path = "/receive-github-event", consumes = "application/json")
    public void receiveGithubWebhookEvent(@RequestBody String requestBody, @RequestHeader  Map<String, String> requestHeaders) {

        LOGGER.info("GitHub webhook event received! Headers: {}, Body: {}", requestHeaders, requestBody);
    }
}
