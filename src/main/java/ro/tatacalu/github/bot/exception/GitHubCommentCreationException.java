package ro.tatacalu.github.bot.exception;

/**
 * Exception thrown when an error occures in the process of creating a GitHub comment.
 */
public class GitHubCommentCreationException extends BaseBotCommandException {

    public GitHubCommentCreationException(String message) {
        super(message);
    }
}
