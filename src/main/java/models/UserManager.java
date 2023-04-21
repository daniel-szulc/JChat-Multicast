package models;

import abstracts.IUserManagement;

import java.util.ArrayList;
import java.util.List;

public class UserManager implements IUserManagement {
    private List<User> users;

    public UserManager() {
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
    public boolean doesUserExist(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
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
    public User getUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }
    @Override
    public void removeUserByName(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                users.remove(user);
                return;
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }
}
