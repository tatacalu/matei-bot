package ro.tatacalu.github.bot.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import ro.tatacalu.github.bot.configuration.MateiBotConfigurationProperties;
import ro.tatacalu.github.bot.domain.IssueComment;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.domain.IssueCommentToCreate;
import ro.tatacalu.github.bot.exception.GitHubCommentCreationException;
import ro.tatacalu.github.bot.util.TestUtils;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link GitHubClientImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class GitHubClientImplTest {

    @Mock
    private MateiBotConfigurationProperties configurationPropertiesMock;

    @Mock
    private RestOperations restOperationsMock;

    @InjectMocks
    private GitHubClientImpl gitHubClient;

    @Before
    public void setUp() {
        when(configurationPropertiesMock.getGithubToken()).thenReturn(TestUtils.TEST_GITHUB_TOKEN);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateGitHubCommentNullCommentsUrl() {
        gitHubClient.createGitHubComment(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateGitHubCommentNullCommentBody() {
        gitHubClient.createGitHubComment(TestUtils.ISSUE_COMMENTS_URL, null);
    }

    @Test
    public void testCreateGitHubCommentSuccess() {
        // request
        HttpEntity<IssueCommentToCreate> httpEntity = TestUtils.createIssueCommentToCreateHttpEntity(TestUtils.HELLO_WORLD_MESSAGE_BODY);

        // response
        HttpHeaders httpHeaders = new HttpHeaders();
        IssueComment issueComment = TestUtils.createIssueCommentGeneric();

        ResponseEntity<IssueComment> responseEntity = new  ResponseEntity<>(issueComment, httpHeaders, HttpStatus.CREATED);

        when(restOperationsMock.exchange(TestUtils.ISSUE_COMMENTS_URL, HttpMethod.POST, httpEntity, IssueComment.class)).thenReturn(responseEntity);

        // call method
        gitHubClient.createGitHubComment(TestUtils.ISSUE_COMMENTS_URL, TestUtils.HELLO_WORLD_MESSAGE_BODY);

        // verify interactions with mocks
        verify(configurationPropertiesMock).getGithubToken();
        verifyNoMoreInteractions(configurationPropertiesMock);

        verify(restOperationsMock).exchange(TestUtils.ISSUE_COMMENTS_URL, HttpMethod.POST, httpEntity, IssueComment.class);
        verifyNoMoreInteractions(restOperationsMock);
    }

    @Test(expected = GitHubCommentCreationException.class)
    public void testCreateGitHubCommentNonCreatedStatus() {
        // request
        HttpEntity<IssueCommentToCreate> httpEntity = TestUtils.createIssueCommentToCreateHttpEntity(TestUtils.HELLO_WORLD_MESSAGE_BODY);

        // response
        HttpHeaders httpHeaders = new HttpHeaders();
        IssueComment issueComment = TestUtils.createIssueCommentGeneric();

        ResponseEntity<IssueComment> responseEntity = new  ResponseEntity<>(issueComment, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);

        when(restOperationsMock.exchange(TestUtils.ISSUE_COMMENTS_URL, HttpMethod.POST, httpEntity, IssueComment.class)).thenReturn(responseEntity);

        // call method
        gitHubClient.createGitHubComment(TestUtils.ISSUE_COMMENTS_URL, TestUtils.HELLO_WORLD_MESSAGE_BODY);

        // verify interactions with mocks
        verify(configurationPropertiesMock).getGithubToken();
        verifyNoMoreInteractions(configurationPropertiesMock);

        verify(restOperationsMock).exchange(TestUtils.ISSUE_COMMENTS_URL, HttpMethod.POST, httpEntity, IssueComment.class);
        verifyNoMoreInteractions(restOperationsMock);
    }
}
