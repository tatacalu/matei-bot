package ro.tatacalu.github.bot.util;

/**
 * Utility class related to HTTP Headers.
 */
public final class HeaderUtils {

    public static final String HEADER_X_GITHUB_EVENT = "x-github-event";
    public static final String HEADER_X_GITHUB_EVENT_ISSUE_COMMENT = HEADER_X_GITHUB_EVENT + "=issue_comment";


    /**
     * Utility class - no instantiation.
     */
    private HeaderUtils() {}
}
