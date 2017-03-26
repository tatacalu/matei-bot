package ro.tatacalu.github.bot.manager;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ro.tatacalu.github.bot.configuration.MateiBotConfigurationProperties;
import ro.tatacalu.github.bot.domain.IssueComment;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.domain.IssueCommentToCreate;

import java.net.URI;

/**
 * Implementation of {@link RepositoryCommentsManager}.
 */
@Component
public class RepositoryCommentsManagerImpl implements RepositoryCommentsManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryCommentsManagerImpl.class);

    @Autowired
    MateiBotConfigurationProperties configurationProperties;

    @Override
    public void processEvent(IssueCommentEvent issueCommentEvent) {
        Preconditions.checkNotNull(issueCommentEvent, "Parameter 'issueCommentEvent' cannot be null");

        if (!RepositoryCommentsManagerHelper.isIargetIssuePullRequest(issueCommentEvent)) {
            LOGGER.info("The received IssueCommentEvent was not triggered by a Pull Request, returning. Event: {}", issueCommentEvent);
            return;
        }

        if (!RepositoryCommentsManagerHelper.isActionCreated(issueCommentEvent)) {
            LOGGER.info("The action of the received IssueCommentEvent was not 'created', returning. Event: {}", issueCommentEvent);
            return;
        }

        // at this stage we have a comment on a pull request

        if (!"@bot say-hello".equals(issueCommentEvent.getComment().getBody())) {
            LOGGER.info("Not a bot hello world comment, returning");
            return;
        }

        URI commentsUrl = issueCommentEvent.getIssue().getCommentsUrl();

        IssueCommentToCreate issueCommentToCreate = new IssueCommentToCreate("bot: hello world");
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", "token " + configurationProperties.getGithubToken());
        headers.add("Accept", "application/vnd.github.v3+json");
        headers.add("Content-Type", "application/json");

        HttpEntity<IssueCommentToCreate> commentToCreate = new HttpEntity<>(issueCommentToCreate, headers);

        LOGGER.info("Creating a new comment...");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<IssueComment> responseEntity = restTemplate.exchange(commentsUrl, HttpMethod.POST, commentToCreate, IssueComment.class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        IssueComment createdIssueComment = responseEntity.getBody();

        LOGGER.info("Comment created: Status code: {}, Response headers: {}, created comment: {}", statusCode, responseHeaders, createdIssueComment);
    }
}
