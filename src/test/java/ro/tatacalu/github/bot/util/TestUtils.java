package ro.tatacalu.github.bot.util;

import ro.tatacalu.github.bot.domain.Issue;
import ro.tatacalu.github.bot.domain.IssueComment;
import ro.tatacalu.github.bot.domain.IssueCommentEvent;
import ro.tatacalu.github.bot.domain.IssuePullRequest;

import java.net.URI;
import java.time.Instant;

/**
 * Utility class used for testing.
 */
public class TestUtils {

    public static final String TEST_GITHUB_TOKEN = "0123456789012345678901234567890123456789";

    public static final String EMPTY_JSON = "{}";
    public static final String EMPTY = "";

    public static final URI COMMENT_URL = URI.create("https://api.github.com/repos/tatacalu/java-patterns/issues/comments/289236192");
    public static final URI COMMENT_ISSUE_URL = URI.create("https://api.github.com/repos/tatacalu/java-patterns/issues/1");
    public static final String COMMENT_BODY_GENERIC = "this is a generic comment body";
    public static final Long COMMENT_ID = 125_000_000L;

    public static final String COMMENT_BODY_BOT_COMMAND_UNSUPPORTED = "@bot unsupported-command";
    public static final String COMMENT_BODY_BOT_COMMAND_SAY_HELLO = "@bot say-hello";
    public static final String BOT_COMMAND_UNSUPPORTED = "unsupported-command";
    public static final String BOT_COMMAND_SAY_HELLO = "say-hello";

    public static final URI ISSUE_PULL_REQUEST_URL = URI.create("https://api.github.com/repos/tatacalu/java-patterns/pulls/1");

    public static final URI ISSUE_URL = COMMENT_ISSUE_URL;
    public static final URI ISSUE_REPOSITORY_URL = URI.create("https://api.github.com/repos/tatacalu/java-patterns");
    public static final URI ISSUE_COMMENTS_URL = URI.create("https://api.github.com/repos/tatacalu/java-patterns/issues/1/comments");
    public static final Long ISSUE_ID = 7_250_000L;
    public static final Long ISSUE_NUMBER = 1L;
    public static final String ISSUE_TITLE = "Pull Request Title";

    /**
     * Utility class - no instantiation.
     */
    private TestUtils() {}

    /**
     * Creates a {@link IssueCommentEvent} instance representing a newly created regular issue, NOT a pull request.
     *
     * @return a new {@link IssueCommentEvent} instance
     */
    public static IssueCommentEvent createCreatedIssueCommentEventRegularIssue() {
        Instant now = Instant.now();
        IssueComment issueComment = new IssueComment(COMMENT_ID, COMMENT_URL, COMMENT_ISSUE_URL, COMMENT_BODY_GENERIC, now, now);
        Issue issue = new Issue(ISSUE_ID, ISSUE_NUMBER, ISSUE_URL, ISSUE_REPOSITORY_URL, ISSUE_COMMENTS_URL, ISSUE_TITLE, now, now, null);

        return new IssueCommentEvent(IssueCommentEvent.ACTION_CREATED, issueComment, issue);
    }

    /**
     * Creates a {@link IssueCommentEvent} instance representing a newly created pull request with a generic, non-bot command issue.
     *
     * @return a new {@link IssueCommentEvent} instance
     */
    public static IssueCommentEvent createCreatedIssueCommentEventPullRequestGeneric() {
        Instant now = Instant.now();
        IssueComment issueComment = new IssueComment(COMMENT_ID, COMMENT_URL, COMMENT_ISSUE_URL, COMMENT_BODY_GENERIC, now, now);
        IssuePullRequest issuePullRequest = new IssuePullRequest(ISSUE_PULL_REQUEST_URL);
        Issue issue = new Issue(ISSUE_ID, ISSUE_NUMBER, ISSUE_URL, ISSUE_REPOSITORY_URL, ISSUE_COMMENTS_URL, ISSUE_TITLE, now, now, issuePullRequest);

        return new IssueCommentEvent(IssueCommentEvent.ACTION_CREATED, issueComment, issue);
    }

    /**
     * Creates a {@link IssueCommentEvent} instance representing a newly created pull request with a generic, non-bot command issue.
     *
     * @return a new {@link IssueCommentEvent} instance
     */
    public static IssueCommentEvent createCreatedIssueCommentEventPullRequestBotCommandHello() {
        Instant now = Instant.now();
        IssueComment issueComment = new IssueComment(COMMENT_ID, COMMENT_URL, COMMENT_ISSUE_URL, COMMENT_BODY_BOT_COMMAND_SAY_HELLO, now, now);
        IssuePullRequest issuePullRequest = new IssuePullRequest(ISSUE_PULL_REQUEST_URL);
        Issue issue = new Issue(ISSUE_ID, ISSUE_NUMBER, ISSUE_URL, ISSUE_REPOSITORY_URL, ISSUE_COMMENTS_URL, ISSUE_TITLE, now, now, issuePullRequest);

        return new IssueCommentEvent(IssueCommentEvent.ACTION_CREATED, issueComment, issue);
    }

    /**
     * Creates a {@link IssueCommentEvent} instance representing an edited pull request.
     *
     * @return a new {@link IssueCommentEvent} instance
     */
    public static IssueCommentEvent createEditedIssueCommentEventPullRequest() {
        Instant now = Instant.now();
        IssueComment issueComment = new IssueComment(COMMENT_ID, COMMENT_URL, COMMENT_ISSUE_URL, COMMENT_BODY_GENERIC, now, now);
        IssuePullRequest issuePullRequest = new IssuePullRequest(ISSUE_PULL_REQUEST_URL);
        Issue issue = new Issue(ISSUE_ID, ISSUE_NUMBER, ISSUE_URL, ISSUE_REPOSITORY_URL, ISSUE_COMMENTS_URL, ISSUE_TITLE, now, now, issuePullRequest);

        return new IssueCommentEvent("edited", issueComment, issue);
    }
}
