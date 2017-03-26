package ro.tatacalu.github.bot.util;

import java.net.URI;

/**
 * Utility class used for testing.
 */
public class TestUtils {

    public static final String TEST_GITHUB_TOKEN = "0123456789012345678901234567890123456789";

    public static final String EMPTY_JSON = "{}";

    public static final URI COMMENT_URL = URI.create("https://api.github.com/repos/tatacalu/java-patterns/issues/comments/289236192");
    public static final URI COMMENT_ISSUE_URL = URI.create("https://api.github.com/repos/tatacalu/java-patterns/issues/1");
    public static final String COMMENT_BODY_GENERIC = "this is a generic comment body";
    public static final Long COMMENT_ID = 125_000_000L;

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
}
