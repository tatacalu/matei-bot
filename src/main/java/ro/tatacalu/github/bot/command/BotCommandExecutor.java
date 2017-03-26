package ro.tatacalu.github.bot.command;

import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.exception.InvalidBotCommandException;

/**
 * Interface for the Bot Command Executor.
 */
public interface BotCommandExecutor {

    /**
     * Execute a bot command related to a {@link IssueCommentEvent}.
     *
     * @param botCommand the command to execute
     * @param issueCommentEvent the event that triggered the command
     * @throws InvalidBotCommandException in case the bot command is not supported
     */
    void execute(String botCommand, IssueCommentEvent issueCommentEvent);
}
