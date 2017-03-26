package ro.tatacalu.github.bot.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.tatacalu.github.bot.exception.BaseBotCommandException;
import ro.tatacalu.github.bot.exception.GitHubCommentCreationException;

/**
 * Controller advice responsible for global exception handling.
 */
@ControllerAdvice
@Slf4j
public class ResponseEntityExceptionHandlerControllerAdvice  {
    @ExceptionHandler
    public ResponseEntity<String> handleGenericException(BaseBotCommandException ex) {
        LOGGER.error("Exception occurred!", ex);

        return new ResponseEntity<>("Exception occurred: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(GitHubCommentCreationException ex) {
        LOGGER.error("GitHubCommentCreationException occurred!", ex);

        return new ResponseEntity<>("Exception occurred while trying to create a GitHub comment: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
