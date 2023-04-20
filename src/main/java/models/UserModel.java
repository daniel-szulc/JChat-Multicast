package models;

import abstracts.IUserModel;

import java.util.ArrayList;
import java.util.List;

public class UserModel implements IUserModel {
    private List<User> users;

    public UserModel() {
        users = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public User getUserByClientId(String clientId) {
        for (User user : users) {
            if (user.getClientId().equals(clientId)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }
}
