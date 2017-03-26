package ro.tatacalu.github.bot.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.tatacalu.github.bot.client.GitHubClient;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.exception.InvalidBotCommandException;
import ro.tatacalu.github.bot.util.TestUtils;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Unit test for (@link BotCommandExecutorImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BotCommandExecutorImplTest {

    @Mock
    private GitHubClient gitHubClientMock;

    @InjectMocks
    private BotCommandExecutorImpl botCommandExecutor;

    @Test(expected = NullPointerException.class)
    public void testExecuteNullBotCommand() {
        botCommandExecutor.execute(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteNullIssueCommentEvent() {
        botCommandExecutor.execute(TestUtils.BOT_COMMAND_SAY_HELLO, null);
    }

    @Test(expected = InvalidBotCommandException.class)
    public void testExecuteUnsupportedBotCommand() {
        IssueCommentEvent issueCommentEvent = TestUtils.createCreatedIssueCommentEventPullRequestGeneric();
        botCommandExecutor.execute(TestUtils.BOT_COMMAND_UNSUPPORTED, issueCommentEvent);
    }

    @Test
    public void testExecuteSayHelloCommand() {
        IssueCommentEvent issueCommentEvent = TestUtils.createCreatedIssueCommentEventPullRequestBotCommandHello();
        botCommandExecutor.execute(TestUtils.BOT_COMMAND_SAY_HELLO, issueCommentEvent);

        verify(gitHubClientMock).createGitHubComment(TestUtils.ISSUE_COMMENTS_URL, "bot: hello world");
        verifyNoMoreInteractions(gitHubClientMock);
    }
}
