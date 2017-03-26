package ro.tatacalu.github.bot.client;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import ro.tatacalu.github.bot.configuration.MateiBotConfigurationProperties;
import ro.tatacalu.github.bot.domain.IssueComment;
import ro.tatacalu.github.bot.domain.IssueCommentToCreate;
import ro.tatacalu.github.bot.exception.GitHubCommentCreationException;
import ro.tatacalu.github.bot.util.MateiBotHeaderUtils;

import java.net.URI;

/**
 * Implementation of the {@link GitHubClient} interface.
 */
@Component
@Slf4j
public class GitHubClientImpl implements GitHubClient {

    private static final String GITHUB_FAILURE_MESSAGE_FORMAT = "Failed to create a GitHub comment. Received status code: %d: %s";

    private final MateiBotConfigurationProperties configurationProperties;
    private final RestOperations restOperations;

    @Autowired
    public GitHubClientImpl(final MateiBotConfigurationProperties configurationProperties, final RestOperations restOperations) {
        this.configurationProperties = configurationProperties;
        this.restOperations = restOperations;
    }

    @Override
    public void createGitHubComment(URI commentsUrl, String commentBody) {
        Preconditions.checkNotNull(commentsUrl, "Parameter 'commentsUrl' cannot be null");
        Preconditions.checkNotNull(commentBody, "Parameter 'commentBody' cannot be null");

        IssueCommentToCreate issueCommentToCreate = new IssueCommentToCreate(commentBody);
        MultiValueMap<String, String> httpHeaders = createHttpHeaders();
        HttpEntity<IssueCommentToCreate> commentToCreate = new HttpEntity<>(issueCommentToCreate, httpHeaders);

        LOGGER.info("Creating a new comment...");

        ResponseEntity<IssueComment> responseEntity = this.restOperations.exchange(commentsUrl, HttpMethod.POST, commentToCreate, IssueComment.class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        IssueComment createdIssueComment = responseEntity.getBody();

        LOGGER.info("Comment creation response: Status code: {}, httpHeaders: {}, created comment: {}", statusCode, responseHeaders, createdIssueComment);

        if (!HttpStatus.CREATED.equals(statusCode)) {
            throw new GitHubCommentCreationException(
                    String.format(GITHUB_FAILURE_MESSAGE_FORMAT, statusCode.value(), statusCode.getReasonPhrase()));
        }
    }

    private MultiValueMap<String, String> createHttpHeaders() {
        MultiValueMap<String, String> httpHeaders = new HttpHeaders();

        httpHeaders.add(HttpHeaders.AUTHORIZATION, String.format(MateiBotHeaderUtils.AUTHORIZATION_HEADER_VALUE_FORMAT, configurationProperties.getGithubToken()));
        httpHeaders.add(HttpHeaders.ACCEPT, MateiBotHeaderUtils.APPLICATION_VND_GITHUB_V3_JSON);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return httpHeaders;
    }
}
