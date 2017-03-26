package ro.tatacalu.github.bot.exception;

import ro.tatacalu.github.bot.domain.IssueCommentEvent;

/**
 * Exception thrown when the bot receives an unsupported {@link IssueCommentEvent}.
 */
public class UnsupportedIssueCommentEventException extends BaseBotCommandException {

    private final IssueCommentEvent issueCommentEvent;

    public UnsupportedIssueCommentEventException(final IssueCommentEvent issueCommentEvent, final String message) {
        super(message);
        this.issueCommentEvent = issueCommentEvent;
    }
}
