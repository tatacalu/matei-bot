package ro.tatacalu.github.bot.manager;

import ro.tatacalu.github.bot.domain.IssueCommentEvent;

/**
 * Interface for the GitHub Repository Comments Manager.
 */
public interface RepositoryCommentsManager {

    void processEvent(IssueCommentEvent issueCommentEvent);
}
