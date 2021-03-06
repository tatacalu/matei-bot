package ro.tatacalu.github.bot.client;

import java.net.URI;

/**
 * GitHub client interface.
 */
public interface GitHubClient {

    /**
     * Create a GitHub pull request comment.
     *
     * @param commentsUrl the Comments API {@link URI} for the GitHub pull request
     * @param commentBody the actual comment message to create
     */
    void createGitHubComment(URI commentsUrl, String commentBody);
}
