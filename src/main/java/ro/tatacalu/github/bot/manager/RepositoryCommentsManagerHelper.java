package ro.tatacalu.github.bot.manager;

import com.google.common.base.Preconditions;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;

/**
 * Helper class used by {@link RepositoryCommentsManagerImpl}.
 */
public final class RepositoryCommentsManagerHelper {

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
    public static boolean isIargetIssuePullRequest(final IssueCommentEvent issueCommentEvent) {
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
}
