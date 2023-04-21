package models;

import utils.UserStatus;

import java.util.UUID;

public class User{
    private String name;
    private final String clientId;
    private Long lastActivity;
    private UserStatus status;

    public User(String name, String clientId) {
        this.name = name;
        this.clientId = clientId;
        status = UserStatus.ACTIVE;
    }

    public User(String name) {
        this(name, UUID.randomUUID().toString());
    }

    public Long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
