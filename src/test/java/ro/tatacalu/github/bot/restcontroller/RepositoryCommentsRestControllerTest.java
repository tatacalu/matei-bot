package ro.tatacalu.github.bot.restcontroller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.manager.RepositoryCommentsManager;
import ro.tatacalu.github.bot.util.TestUtils;

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
    private RepositoryCommentsManager repositoryCommentsManagerMock;

    private RepositoryCommentsRestController repositoryCommentsRestController;

    @Before
    public void setUp() {
        repositoryCommentsRestController = new RepositoryCommentsRestController(repositoryCommentsManagerMock);
    }

    @Test
    public void testUnsupportedGithubEvent() {
        ResponseEntity<String> responseEntity = repositoryCommentsRestController.receiveGithubWebhookEvent(TestUtils.EMPTY_JSON, Collections.emptyMap(),
                "unsupportedEventType");

        assertThat(responseEntity, is(notNullValue()));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(responseEntity.getBody(), is(notNullValue()));
        assertThat(responseEntity.getBody(), is("Unsupported GitHub webhook event received! Event type: [unsupportedEventType]"));

        verifyNoMoreInteractions(repositoryCommentsManagerMock);
    }

    @Test
    public void testGithubWebhookIssueCommentEvent() {
        IssueCommentEvent issueCommentEvent = TestUtils.createCreatedIssueCommentEventRegularIssue();

        ResponseEntity responseEntity = repositoryCommentsRestController.receiveGithubWebhookIssueCommentEvent(issueCommentEvent, Collections.emptyMap());

        assertThat(responseEntity, is(notNullValue()));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        verify(repositoryCommentsManagerMock).processBotCommandEvent(issueCommentEvent);
        verifyNoMoreInteractions(repositoryCommentsManagerMock);
    }
}
