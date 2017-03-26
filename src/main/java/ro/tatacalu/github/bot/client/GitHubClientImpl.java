package ro.tatacalu.github.bot.client;

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
import org.springframework.web.client.RestTemplate;
import ro.tatacalu.github.bot.configuration.MateiBotConfigurationProperties;
import ro.tatacalu.github.bot.domain.IssueComment;
import ro.tatacalu.github.bot.domain.IssueCommentToCreate;
import ro.tatacalu.github.bot.exception.GitHubCommentCreationException;

import java.net.URI;

/**
 * Implementation of the {@link GitHubClient} interface.
 */
@Component
@Slf4j
public class GitHubClientImpl implements GitHubClient {

    private static final String APPLICATION_VND_GITHUB_V3_JSON = "application/vnd.github.v3+json";
    private static final String AUTHORIZATION_HEADER_VALUE_FORMAT = "token %s";
    private static final String GITHUB_FAILURE_MESSAGE_FORMAT = "Failed to create a GitHub comment. Received status code: %i: %s";

    @Autowired
    private MateiBotConfigurationProperties configurationProperties;

    @Override
    public void createGitHubComment(URI commentsUrl, String commentBody) {
        IssueCommentToCreate issueCommentToCreate = new IssueCommentToCreate(commentBody);
        MultiValueMap<String, String> httpHeaders = createHttpHeaders();
        HttpEntity<IssueCommentToCreate> commentToCreate = new HttpEntity<>(issueCommentToCreate, httpHeaders);

        LOGGER.info("Creating a new comment...");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<IssueComment> responseEntity = restTemplate.exchange(commentsUrl, HttpMethod.POST, commentToCreate, IssueComment.class);

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

        httpHeaders.add(HttpHeaders.AUTHORIZATION, String.format(AUTHORIZATION_HEADER_VALUE_FORMAT, configurationProperties.getGithubToken()));
        httpHeaders.add(HttpHeaders.ACCEPT, APPLICATION_VND_GITHUB_V3_JSON);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return httpHeaders;
    }
}
