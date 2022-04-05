package edu.byu.cs.tweeter.model.net.request;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class UpdateFeedsRequest {
    private Status status;
    private List<User> users;

    private UpdateFeedsRequest(){}

    public UpdateFeedsRequest(Status status, List<User> aliases) {
        this.status = status;
        this.users = aliases;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
