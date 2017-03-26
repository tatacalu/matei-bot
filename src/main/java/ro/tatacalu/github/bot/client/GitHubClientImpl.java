package ro.tatacalu.github.bot.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.net.URI;

/**
 * Implementation of the {@link GitHubClient} interface.
 */
@Component
public class GitHubClientImpl implements GitHubClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubClientImpl.class);

    private static final String APPLICATION_VND_GITHUB_V3_JSON = "application/vnd.github.v3+json";
    private static final String AUTHORIZATION_HEADER_VALUE_FORMAT = "token %s";

    @Autowired
    private MateiBotConfigurationProperties configurationProperties;

    @Override
    public boolean createGitHubComment(URI commentsUrl, String commentBody) {
        IssueCommentToCreate issueCommentToCreate = new IssueCommentToCreate(commentBody);
        MultiValueMap<String, String> httpHeaders = createHttpHeaders();
        HttpEntity<IssueCommentToCreate> commentToCreate = new HttpEntity<>(issueCommentToCreate, httpHeaders);

        LOGGER.info("Creating a new comment...");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<IssueComment> responseEntity = restTemplate.exchange(commentsUrl, HttpMethod.POST, commentToCreate, IssueComment.class);

        HttpStatus statusCode = responseEntity.getStatusCode();
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        IssueComment createdIssueComment = responseEntity.getBody();

        LOGGER.info("Comment created: Status code: {}, Response httpHeaders: {}, created comment: {}", statusCode, responseHeaders, createdIssueComment);
        return true;
    }

    private MultiValueMap<String, String> createHttpHeaders() {
        MultiValueMap<String, String> httpHeaders = new HttpHeaders();

        httpHeaders.add(HttpHeaders.AUTHORIZATION, String.format(AUTHORIZATION_HEADER_VALUE_FORMAT, configurationProperties.getGithubToken()));
        httpHeaders.add(HttpHeaders.ACCEPT, APPLICATION_VND_GITHUB_V3_JSON);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return httpHeaders;
    }
}
