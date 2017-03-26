package ro.tatacalu.github.bot.domain;

import lombok.Value;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Domain model class representing a GitHub comment to be created.
 */
@Value
public class IssueCommentToCreate {

    @NotNull
    @NotEmpty
    String body;
}
