package ro.tatacalu.github.bot.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Domain model class representing a GitHub Issue Comment Event.
 */
public class IssueCommentEvent {

    @NotBlank
    @Pattern(regexp = "^created|edited|deleted$", message = "action field can only have one of the following values: \"created\", \"edited\", \"deleted\"")
    private String action;

    @NotNull
    @Valid
    private IssueComment comment;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public IssueComment getComment() {
        return comment;
    }

    public void setComment(IssueComment comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueCommentEvent that = (IssueCommentEvent) o;

        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        return comment != null ? comment.equals(that.comment) : that.comment == null;
    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueCommentEvent{" +
                "action='" + action + '\'' +
                ", comment=" + comment +
                '}';
    }
}
