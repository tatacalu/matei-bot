package ro.tatacalu.github.bot.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.util.HeaderUtils;

import java.util.Map;

/**
 * REST Controller called by a GitHub repository webhook.
 */
@RestController
public class RepositoryCommentsRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryCommentsRestController.class);

    public static final String REQUEST_MAPPING_PATH = "/receive-github-event";

    /**
     * Method called when a GitHub event of an unsupported type not explicitly dealt with has been received by the application. The event type is transmitted
     * in the 'x-github-event' HTTP header.
     *
     * @param requestBody the JSON payload that has been received as part of the HTTP POST Request
     * @param requestHeaders the HTTP headers that have been received as part of the HTTP POST Request
     */
    @PostMapping(path = REQUEST_MAPPING_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void receiveGithubWebhookEvent(@RequestBody final String requestBody, @RequestHeader final Map<String, String> requestHeaders,
                                          @RequestHeader(HeaderUtils.HEADER_X_GITHUB_EVENT) final String githubEventType) {

        LOGGER.warn("Unsupported GitHub webhook event received! Event type: [{}], Headers: {}, Body: {}", githubEventType, requestHeaders, requestBody);
    }

    /**
     * Method called when a GitHub event of type 'issue_comment' has been received by the application. The event type is transmitted in the
     * 'x-github-event' HTTP header.
     *
     * @param issueCommentEvent the {link IssueCommentEvent} received as part of the HTTP POST Request
     * @param requestHeaders the HTTP headers that have been received as part of the HTTP POST Request
     */
    @PostMapping(path = REQUEST_MAPPING_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, headers = {HeaderUtils.HEADER_X_GITHUB_EVENT_ISSUE_COMMENT})
    public void receiveGithubWebhookIssueCommentEvent(@RequestBody @Validated final IssueCommentEvent issueCommentEvent,
                                                      @RequestHeader final Map<String, String> requestHeaders) {

        LOGGER.info("GitHub issue_comment webhook event received! Headers: {}, IssueCommentEvent: {}", requestHeaders, issueCommentEvent);
    }
}
