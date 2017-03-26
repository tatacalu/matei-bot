package ro.tatacalu.github.bot.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tatacalu.github.bot.client.GitHubClient;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;

/**
 * Implementation of the {@link BotCommandExecutor} interface.
 */
@Component
public class BotCommandExecutorImpl implements BotCommandExecutor {

    private static final String SAY_HELLO_COMMAND = "say-hello";
    private static final String HELLO_WORLD_MESSAGE_BODY = "bot: hello world v2";

    @Autowired
    private GitHubClient gitHubClient;

    @Override
    public boolean execute(String botCommand, IssueCommentEvent issueCommentEvent) {

        if (SAY_HELLO_COMMAND.equals(botCommand)) {
            return executeSayHelloCommand(issueCommentEvent);
        }

        return false;
    }

    private boolean executeSayHelloCommand(IssueCommentEvent issueCommentEvent) {
        return gitHubClient.createGitHubComment(issueCommentEvent.getIssue().getCommentsUrl(), HELLO_WORLD_MESSAGE_BODY);
    }
}
