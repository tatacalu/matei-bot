package ro.tatacalu.github.bot.command;

import ro.tatacalu.github.bot.domain.IssueCommentEvent;

/**
 * Interface for the Bot Command Executor.
 */
public interface BotCommandExecutor {

    /**
     * Execute a bot command related to a {@link IssueCommentEvent}.
     *
     * @param botCommand the command to execute
     * @param issueCommentEvent the event that triggered the command
     * @return true of the command was successful, false otherwise
     */
    boolean execute(String botCommand, IssueCommentEvent issueCommentEvent);
}
