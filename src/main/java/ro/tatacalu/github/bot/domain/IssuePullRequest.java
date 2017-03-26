package ro.tatacalu.github.bot.domain;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * Domain model class for holding the details of a GitHub Issue Pull Request.
 */
@Value
public class IssuePullRequest {

    @NotNull
    URI url;
}
