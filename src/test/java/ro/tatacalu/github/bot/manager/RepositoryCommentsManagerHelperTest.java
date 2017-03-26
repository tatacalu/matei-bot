package ro.tatacalu.github.bot.manager;

import org.junit.Test;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.util.TestUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit test for {@link RepositoryCommentsManagerHelper}.
 */
public class RepositoryCommentsManagerHelperTest {

    @Test(expected = NullPointerException.class)
    public void testIsBotCommandNull() {
        RepositoryCommentsManagerHelper.isBotCommand(null);
    }

    @Test
    public void testIsBotCommandEmpty() {
        boolean isBotCommand = RepositoryCommentsManagerHelper.isBotCommand(TestUtils.EMPTY);
        assertThat(isBotCommand, is(false));
    }

    @Test
    public void testIsBotCommand() {
        boolean isBotCommand = RepositoryCommentsManagerHelper.isBotCommand(TestUtils.COMMENT_BODY_GENERIC);
        assertThat(isBotCommand, is(false));

        boolean isBotCommandUnsupported = RepositoryCommentsManagerHelper.isBotCommand(TestUtils.COMMENT_BODY_BOT_COMMAND_UNSUPPORTED);
        assertThat(isBotCommandUnsupported, is(true));

        boolean isBotCommandSayHello = RepositoryCommentsManagerHelper.isBotCommand(TestUtils.COMMENT_BODY_BOT_COMMAND_SAY_HELLO);
        assertThat(isBotCommandSayHello, is(true));
    }

    @Test(expected = RuntimeException.class)
    public void testGetBotCommandIllegalMessage() {
        RepositoryCommentsManagerHelper.getBotCommand(TestUtils.COMMENT_BODY_GENERIC);
    }

    @Test
    public void testGetBotCommand() {
        String botCommandUnsupported = RepositoryCommentsManagerHelper.getBotCommand(TestUtils.COMMENT_BODY_BOT_COMMAND_UNSUPPORTED);
        assertThat(botCommandUnsupported, is(notNullValue()));
        assertThat(botCommandUnsupported, is(TestUtils.BOT_COMMAND_UNSUPPORTED));

        String botCommandSayHello = RepositoryCommentsManagerHelper.getBotCommand(TestUtils.COMMENT_BODY_BOT_COMMAND_SAY_HELLO);
        assertThat(botCommandSayHello, is(notNullValue()));
        assertThat(botCommandSayHello, is(TestUtils.BOT_COMMAND_SAY_HELLO));
    }

    @Test(expected = NullPointerException.class)
    public void testIsTargetIssuePullRequestNull() {
        RepositoryCommentsManagerHelper.isTargetIssuePullRequest(null);
    }

    @Test
    public void testIsTargetIssuePullRequest() {
        IssueCommentEvent issueCommentEvent = TestUtils.createCreatedIssueCommentEventRegularIssue();
        boolean isTargetIssuePullRequest = RepositoryCommentsManagerHelper.isTargetIssuePullRequest(issueCommentEvent);
        assertThat(isTargetIssuePullRequest, is(false));

        IssueCommentEvent issueCommentEventPullRequest = TestUtils.createCreatedIssueCommentEventPullRequestGeneric();
        boolean isTargetIssuePullRequestPR = RepositoryCommentsManagerHelper.isTargetIssuePullRequest(issueCommentEventPullRequest);
        assertThat(isTargetIssuePullRequestPR, is(true));
    }

    @Test(expected = NullPointerException.class)
    public void testIsActionCreatedNull() {
        RepositoryCommentsManagerHelper.isActionCreated(null);
    }

    @Test
    public void testIsActionCreated() {
        IssueCommentEvent createdIssueCommentEventPullRequest = TestUtils.createCreatedIssueCommentEventPullRequestGeneric();
        boolean actionCreated = RepositoryCommentsManagerHelper.isActionCreated(createdIssueCommentEventPullRequest);
        assertThat(actionCreated, is(true));

        IssueCommentEvent editedIssueCommentEventPullRequest = TestUtils.createEditedIssueCommentEventPullRequest();
        boolean editedActionCreated = RepositoryCommentsManagerHelper.isActionCreated(editedIssueCommentEventPullRequest);
        assertThat(editedActionCreated, is(false));
    }
}
