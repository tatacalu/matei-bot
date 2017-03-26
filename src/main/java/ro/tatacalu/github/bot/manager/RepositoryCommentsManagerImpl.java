package ro.tatacalu.github.bot.manager;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tatacalu.github.bot.command.BotCommandExecutor;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.exception.UnsupportedIssueCommentEventException;

/**
 * Implementation of {@link RepositoryCommentsManager}.
 */
@Component
@Slf4j
public class RepositoryCommentsManagerImpl implements RepositoryCommentsManager {

    private final BotCommandExecutor botCommandExecutor;

    @Autowired
    public RepositoryCommentsManagerImpl(final BotCommandExecutor botCommandExecutor) {
        this.botCommandExecutor = botCommandExecutor;
    }

    @Override
    public void processBotCommandEvent(IssueCommentEvent issueCommentEvent) {
        Preconditions.checkNotNull(issueCommentEvent, "Parameter 'issueCommentEvent' cannot be null");

        if (!RepositoryCommentsManagerHelper.isTargetIssuePullRequest(issueCommentEvent)) {
            LOGGER.info("The received IssueCommentEvent was not triggered by a Pull Request. Event: {}", issueCommentEvent);
            throw new UnsupportedIssueCommentEventException(issueCommentEvent, "The received IssueCommentEvent was not triggered by a Pull Request");
        }

        if (!RepositoryCommentsManagerHelper.isActionCreated(issueCommentEvent)) {
            LOGGER.info("The action of the received IssueCommentEvent was not 'created'. Event: {}", issueCommentEvent);
            throw new UnsupportedIssueCommentEventException(issueCommentEvent, "The action of the received IssueCommentEvent was not 'created'");
        }

        String commentMessage = issueCommentEvent.getComment().getBody();
        if (!RepositoryCommentsManagerHelper.isBotCommand(commentMessage)) {
            LOGGER.info("Received comment message is not a bot command, returning. Message: [{}]", commentMessage);
            throw new UnsupportedIssueCommentEventException(issueCommentEvent, "Received comment message is not a bot command");
        }

        String botCommand = RepositoryCommentsManagerHelper.getBotCommand(commentMessage);
        botCommandExecutor.execute(botCommand, issueCommentEvent);
    }
}
