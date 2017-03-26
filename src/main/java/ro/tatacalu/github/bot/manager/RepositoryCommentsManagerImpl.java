package ro.tatacalu.github.bot.manager;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ro.tatacalu.github.bot.command.BotCommandExecutor;
import ro.tatacalu.github.bot.configuration.MateiBotConfigurationProperties;
import ro.tatacalu.github.bot.domain.IssueComment;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.domain.IssueCommentToCreate;

import java.net.URI;

/**
 * Implementation of {@link RepositoryCommentsManager}.
 */
@Component
public class RepositoryCommentsManagerImpl implements RepositoryCommentsManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryCommentsManagerImpl.class);

    @Autowired
    MateiBotConfigurationProperties configurationProperties;

    @Autowired
    private BotCommandExecutor botCommandExecutor;

    @Override
    public void processEvent(IssueCommentEvent issueCommentEvent) {
        Preconditions.checkNotNull(issueCommentEvent, "Parameter 'issueCommentEvent' cannot be null");

        if (!RepositoryCommentsManagerHelper.isIargetIssuePullRequest(issueCommentEvent)) {
            LOGGER.info("The received IssueCommentEvent was not triggered by a Pull Request, returning. Event: {}", issueCommentEvent);
            return;
        }

        if (!RepositoryCommentsManagerHelper.isActionCreated(issueCommentEvent)) {
            LOGGER.info("The action of the received IssueCommentEvent was not 'created', returning. Event: {}", issueCommentEvent);
            return;
        }

        // at this stage we have a comment on a pull request

        String commentMessage = issueCommentEvent.getComment().getBody();
        if (!RepositoryCommentsManagerHelper.isBotCommand(commentMessage)) {
            LOGGER.info("Received comment message is not a bot command, returning. Message: [{}]", commentMessage);
            return;
        }

        String botCommand = RepositoryCommentsManagerHelper.getBotCommand(commentMessage);

        boolean commandExecutedSuccessfully = botCommandExecutor.execute(botCommand, issueCommentEvent);
    }
}
