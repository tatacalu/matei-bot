package ro.tatacalu.github.bot.util;

/**
 * Utility class related to HTTP Headers.
 */
public final class MateiBotHeaderUtils {

    public static final String HEADER_X_GITHUB_EVENT = "x-github-event";
    public static final String HEADER_X_GITHUB_EVENT_ISSUE_COMMENT = HEADER_X_GITHUB_EVENT + "=issue_comment";
    public static final String APPLICATION_VND_GITHUB_V3_JSON = "application/vnd.github.v3+json";
    public static final String AUTHORIZATION_HEADER_VALUE_FORMAT = "token %s";

    /**
     * Utility class - no instantiation.
     */
    private MateiBotHeaderUtils() {}
}
