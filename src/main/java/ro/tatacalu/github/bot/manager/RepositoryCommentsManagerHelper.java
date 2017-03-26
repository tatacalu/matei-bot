package ro.tatacalu.github.bot.manager;

import com.google.common.base.Preconditions;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;

/**
 * Helper class used by {@link RepositoryCommentsManagerImpl}.
 */
public final class RepositoryCommentsManagerHelper {

    private static final String BOT_COMMAND_PREFIX = "@bot ";

    /**
     * Utility class - no instantiation.
     */
    private RepositoryCommentsManagerHelper() {}

    /**
     * Checks if a {@link IssueCommentEvent} instance represents a comment on a GitHub pull request or a comment on a general GitHub issue.
     *
     * @param issueCommentEvent the event to analyse
     * @return true if the comment has been made on a pull request, false otherwise
     */
    public static boolean isTargetIssuePullRequest(final IssueCommentEvent issueCommentEvent) {
        Preconditions.checkNotNull(issueCommentEvent, "Parameter 'issueCommentEvent' cannot be null");

        return (issueCommentEvent.getIssue() != null) && (issueCommentEvent.getIssue().getPullRequest() != null) && (issueCommentEvent.getIssue().getPullRequest().getUrl() != null);
    }

    /**
     * Checks if the action of a {@link IssueCommentEvent} is 'created'.
     *
     * @param issueCommentEvent the event to analyse
     * @return true if the action is 'created', false otherwise
     */
    public static boolean isActionCreated(final IssueCommentEvent issueCommentEvent) {
        Preconditions.checkNotNull(issueCommentEvent, "Parameter 'issueCommentEvent' cannot be null");

        return IssueCommentEvent.ACTION_CREATED.equals(issueCommentEvent.getAction());
    }

    /**
     * Checks if the provided message is a command for the bot. Bot commands start with "@bot ".
     *
     * @param message the message to analyse
     * @return true if the message is a bot command, false otherwise
     */
    public static boolean isBotCommand(String message) {
        Preconditions.checkNotNull(message, "Parameter 'message' cannot be null");

        String trimmedMessage = message.trim();
        return !BOT_COMMAND_PREFIX.equals(trimmedMessage) && trimmedMessage.startsWith(BOT_COMMAND_PREFIX);
    }

    /**
     * Retrieves the bot command from a provided message.
     *
     * @param message the message to analyse
     * @return the command addressed to the bot
     * @throws RuntimeException if the provided message is not a bot command
     */
    public static String getBotCommand(String message) {
        if (isBotCommand(message)) {
            return message.trim().substring(BOT_COMMAND_PREFIX.length());
        }

        throw new RuntimeException("Message is not a bot command!");
    }
}
