package ro.tatacalu.github.bot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * Domain model class representing a GitHub Issue Comment.
 *
 * Getters, setters, equals(), hashCode() and toString() are generated by Lombok.
 */
@Data
public class IssueComment {

    @NotNull
    private Long id;

    @NotNull
    private URI url;

    @NotNull
    @JsonProperty("issue_url")
    private URI issueUrl;

    @NotNull
    @NotEmpty
    private String body;
}
