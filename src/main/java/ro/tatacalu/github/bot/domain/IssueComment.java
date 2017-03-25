package ro.tatacalu.github.bot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * Domain model class representing a GitHub Issue Comment.
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public URI getIssueUrl() {
        return issueUrl;
    }

    public void setIssueUrl(URI issueUrl) {
        this.issueUrl = issueUrl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssueComment that = (IssueComment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (issueUrl != null ? !issueUrl.equals(that.issueUrl) : that.issueUrl != null) return false;
        return body != null ? body.equals(that.body) : that.body == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (issueUrl != null ? issueUrl.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IssueComment{" +
                "id=" + id +
                ", url=" + url +
                ", issueUrl=" + issueUrl +
                ", body='" + body + '\'' +
                '}';
    }
}
