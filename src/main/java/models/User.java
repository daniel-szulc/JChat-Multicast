package models;

import utils.UserStatus.*;

import java.util.UUID;

public class User {
    private String nickname;
    private final String clientId;
    private Long lastActivity;
    private Status status;

    public User(String nickname, String clientId) {
        this.nickname = nickname;
        this.clientId = clientId;
        status = Status.ACTIVE;
    }

    public User(String nickname) {
        this(nickname, UUID.randomUUID().toString());
    }

    public Long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Long lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getClientId() {
        return clientId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
