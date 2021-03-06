package ro.tatacalu.github.bot.domain;

import lombok.Value;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Domain model class representing a GitHub Issue Comment Event.
 *
 * Getters, setters, equals(), hashCode() and toString() are generated by Lombok.
 */
@Value
public class IssueCommentEvent {

    public static final String ACTION_CREATED = "created";

    @NotBlank
    @Pattern(regexp = "^created|edited|deleted$", message = "action field can only have one of the following values: \"created\", \"edited\", \"deleted\"")
    String action;

    @NotNull
    @Valid
    IssueComment comment;

    @NotNull
    @Valid
    Issue issue;
}
