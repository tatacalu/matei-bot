package ro.tatacalu.github.bot.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.manager.RepositoryCommentsManager;
import ro.tatacalu.github.bot.util.MateiBotHeaderUtils;

import java.util.Map;

/**
 * REST Controller called by a GitHub repository webhook.
 */
@RestController
@Slf4j
public class RepositoryCommentsRestController {

    private static final String UNSUPPORTED_EVENT_TYPE_FORMAT = "Unsupported GitHub webhook event received! Event type: [%s]";

    private static final String REQUEST_MAPPING_PATH = "/receive-github-event";

    private final RepositoryCommentsManager repositoryCommentsManager;

    @Autowired
    public RepositoryCommentsRestController(final RepositoryCommentsManager repositoryCommentsManager) {
        this.repositoryCommentsManager = repositoryCommentsManager;
    }

    /**
     * Method called when a GitHub event of an unsupported type not explicitly dealt with has been received by the application. The event type is transmitted
     * in the 'x-github-event' HTTP header.
     *
     * @param requestBody the JSON payload that has been received as part of the HTTP POST Request
     * @param requestHeaders the HTTP headers that have been received as part of the HTTP POST Request
     */
    @PostMapping(path = REQUEST_MAPPING_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> receiveGithubWebhookEvent(@RequestBody final String requestBody, @RequestHeader final Map<String, String> requestHeaders,
                                          @RequestHeader(MateiBotHeaderUtils.HEADER_X_GITHUB_EVENT) final String githubEventType) {

        LOGGER.warn("Unsupported GitHub webhook event received! Event type: [{}], Headers: {}, Body: {}", githubEventType, requestHeaders, requestBody);

        return new ResponseEntity<>(String.format(UNSUPPORTED_EVENT_TYPE_FORMAT, githubEventType), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method called when a GitHub event of type 'issue_comment' has been received by the application. The event type is transmitted in the
     * 'x-github-event' HTTP header.
     *
     * @param issueCommentEvent the {link IssueCommentEvent} received as part of the HTTP POST Request
     * @param requestHeaders the HTTP headers that have been received as part of the HTTP POST Request
     */
    @PostMapping(path = REQUEST_MAPPING_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, headers = {MateiBotHeaderUtils.HEADER_X_GITHUB_EVENT_ISSUE_COMMENT})
    public ResponseEntity receiveGithubWebhookIssueCommentEvent(@RequestBody @Validated final IssueCommentEvent issueCommentEvent,
                                                                @RequestHeader final Map<String, String> requestHeaders) {

        LOGGER.info("GitHub issue_comment webhook event received! Headers: {}, IssueCommentEvent: {}", requestHeaders, issueCommentEvent);

        repositoryCommentsManager.processBotCommandEvent(issueCommentEvent);

        return new ResponseEntity(HttpStatus.OK);
    }
}
