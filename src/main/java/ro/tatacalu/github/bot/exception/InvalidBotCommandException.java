package ro.tatacalu.github.bot.exception;

/**
 * Exception thrown when the bot receives an invalid command.
 */
public class InvalidBotCommandException extends BaseBotCommandException {

    public InvalidBotCommandException(String message) {
        super(message);
    }
}
