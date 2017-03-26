package ro.tatacalu.github.bot.restcontroller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.tatacalu.github.bot.domain.Issue;
import ro.tatacalu.github.bot.domain.IssueComment;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.manager.RepositoryCommentsManager;
import ro.tatacalu.github.bot.util.TestUtils;

import java.time.Instant;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Unit test for (@link RepositoryCommentsRestController}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryCommentsRestControllerTest {

    @Mock
    private RepositoryCommentsManager repositoryCommentsManager;

    private RepositoryCommentsRestController repositoryCommentsRestController;

    @Before
    public void setUp() {
        repositoryCommentsRestController = new RepositoryCommentsRestController(repositoryCommentsManager);
    }

    @Test
    public void testUnsupportedGithubEvent() {
        ResponseEntity<String> responseEntity = repositoryCommentsRestController.receiveGithubWebhookEvent(TestUtils.EMPTY_JSON, Collections.emptyMap(),
                "unsupportedEventType");

        assertThat(responseEntity, is(notNullValue()));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(responseEntity.getBody(), is(notNullValue()));
        assertThat(responseEntity.getBody(), is("Unsupported GitHub webhook event received! Event type: [unsupportedEventType]"));

        verifyNoMoreInteractions(repositoryCommentsManager);
    }

    @Test
    public void testGithubWebhookIssueCommentEvent() {
        IssueCommentEvent issueCommentEvent = createIssueCommentEvent();

        ResponseEntity responseEntity = repositoryCommentsRestController.receiveGithubWebhookIssueCommentEvent(issueCommentEvent, Collections.emptyMap());

        assertThat(responseEntity, is(notNullValue()));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        verify(repositoryCommentsManager).processBotCommandEvent(issueCommentEvent);
        verifyNoMoreInteractions(repositoryCommentsManager);
    }

    private IssueCommentEvent createIssueCommentEvent() {
        Instant now = Instant.now();
        IssueComment issueComment = new IssueComment(TestUtils.COMMENT_ID, TestUtils.COMMENT_URL, TestUtils.COMMENT_ISSUE_URL, TestUtils.COMMENT_BODY_GENERIC,
                now, now);
        Issue issue = new Issue(TestUtils.ISSUE_ID, TestUtils.ISSUE_NUMBER, TestUtils.ISSUE_URL, TestUtils.ISSUE_REPOSITORY_URL, TestUtils.ISSUE_COMMENTS_URL,
                TestUtils.ISSUE_TITLE, now, now, null);

        return new IssueCommentEvent(IssueCommentEvent.ACTION_CREATED, issueComment, issue);
    }
}
