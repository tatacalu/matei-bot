package ro.tatacalu.github.bot.command;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tatacalu.github.bot.client.GitHubClient;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.exception.InvalidBotCommandException;

/**
 * Implementation of the {@link BotCommandExecutor} interface.
 */
@Component
@Slf4j
public class BotCommandExecutorImpl implements BotCommandExecutor {

    private static final String SAY_HELLO_COMMAND = "say-hello";
    private static final String HELLO_WORLD_MESSAGE_BODY = "bot: hello world";

    private final GitHubClient gitHubClient;

    @Autowired
    public BotCommandExecutorImpl(final GitHubClient gitHubClient) {
        this.gitHubClient = gitHubClient;
    }

    @Override
    public void execute(String botCommand, IssueCommentEvent issueCommentEvent) {
        Preconditions.checkNotNull(botCommand, "Parameter 'botCommand' cannot be null");
        Preconditions.checkNotNull(issueCommentEvent, "Parameter 'issueCommentEvent' cannot be null");

        if (SAY_HELLO_COMMAND.equals(botCommand)) {
            executeSayHelloCommand(issueCommentEvent);
            return;
        }

        throw new InvalidBotCommandException(String.format("Invalid bot command [%s]", botCommand));
    }

    private void executeSayHelloCommand(IssueCommentEvent issueCommentEvent) {
        LOGGER.debug("Executing the [{}] command...", SAY_HELLO_COMMAND);
        gitHubClient.createGitHubComment(issueCommentEvent.getIssue().getCommentsUrl(), HELLO_WORLD_MESSAGE_BODY);
    }
}
