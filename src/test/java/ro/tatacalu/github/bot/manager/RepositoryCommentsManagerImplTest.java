package ro.tatacalu.github.bot.manager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.tatacalu.github.bot.command.BotCommandExecutor;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.exception.UnsupportedIssueCommentEventException;
import ro.tatacalu.github.bot.util.TestUtils;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Unit test for {@link RepositoryCommentsManagerImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryCommentsManagerImplTest {

    @Mock
    private BotCommandExecutor botCommandExecutorMock;

    @InjectMocks
    private RepositoryCommentsManagerImpl repositoryCommentsManager;

    @Test(expected = NullPointerException.class)
    public void testProcessBotCommandEventNull() {
        repositoryCommentsManager.processBotCommandEvent(null);
        verifyNoMoreInteractions(botCommandExecutorMock);
    }

    @Test(expected = UnsupportedIssueCommentEventException.class)
    public void testProcessBotCommandEventNonPullRequest() {
        IssueCommentEvent issueCommentEvent = TestUtils.createCreatedIssueCommentEventRegularIssue();
        repositoryCommentsManager.processBotCommandEvent(issueCommentEvent);
        verifyNoMoreInteractions(botCommandExecutorMock);
    }

    @Test(expected = UnsupportedIssueCommentEventException.class)
    public void testProcessBotCommandEventEditedPullRequest() {
        IssueCommentEvent issueCommentEvent = TestUtils.createEditedIssueCommentEventPullRequest();
        repositoryCommentsManager.processBotCommandEvent(issueCommentEvent);
        verifyNoMoreInteractions(botCommandExecutorMock);
    }

    @Test(expected = UnsupportedIssueCommentEventException.class)
    public void testProcessBotCommandEventCreatedPullRequestGeneric() {
        IssueCommentEvent issueCommentEvent = TestUtils.createCreatedIssueCommentEventPullRequestGeneric();
        repositoryCommentsManager.processBotCommandEvent(issueCommentEvent);
        verifyNoMoreInteractions(botCommandExecutorMock);
    }

    @Test
    public void testProcessBotCommandEventCreatedPullRequestBotCommand() {
        IssueCommentEvent issueCommentEvent = TestUtils.createCreatedIssueCommentEventPullRequestBotCommandHello();
        repositoryCommentsManager.processBotCommandEvent(issueCommentEvent);

        verify(botCommandExecutorMock).execute(TestUtils.BOT_COMMAND_SAY_HELLO, issueCommentEvent);
        verifyNoMoreInteractions(botCommandExecutorMock);
    }
}
