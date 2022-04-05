package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

public class PostFollowersResponse extends Response {

    private List<String> aliases;

    public PostFollowersResponse(String message) {
        super(false, message);
    }

    public PostFollowersResponse(List<String> aliases) {
        super(true);
        this.aliases = aliases;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        PostFollowersResponse that = (PostFollowersResponse) param;

        return (Objects.equals(aliases, that.aliases) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliases);
    }
}
