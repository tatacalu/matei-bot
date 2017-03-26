package ro.tatacalu.github.bot.exception;

/**
 * Base class for Bot Command Exceptions.
 */
public class BaseBotCommandException extends RuntimeException {

    public BaseBotCommandException(String message) {
        super(message);
    }
}
