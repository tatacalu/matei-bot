package ro.tatacalu.github.bot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.Instant;

/**
 * Domain model class representing a GitHub Issue.
 *
 * GitHub issues can represent either general tickets or pull requests.
 */
@Value
public class Issue {

    @NotNull
    Long id;

    @NotNull
    Long number;

    @NotNull
    URI url;

    @NotNull
    @JsonProperty("repository_url")
    URI repositoryUrl;

    @NotNull
    @JsonProperty("comments_url")
    URI commentsUrl;

    @NotNull
    @NotEmpty
    String title;

    @NotNull
    @JsonProperty("created_at")
    Instant createdAt;

    @NotNull
    @JsonProperty("updated_at")
    Instant updatedAt;

    @Valid
    @JsonProperty("pull_request")
    IssuePullRequest pullRequest;
}
