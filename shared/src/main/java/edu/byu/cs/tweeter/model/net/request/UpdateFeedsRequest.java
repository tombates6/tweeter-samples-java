package edu.byu.cs.tweeter.model.net.request;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class UpdateFeedsRequest {
    private Status status;
    private List<String> aliases;

    private UpdateFeedsRequest(){}

    public UpdateFeedsRequest(Status status, List<String> aliases) {
        this.status = status;
        this.aliases = aliases;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
