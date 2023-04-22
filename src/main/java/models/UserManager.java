package models;

import abstracts.IUserManagement;
import utils.UserStatus;

import java.util.ArrayList;
import java.util.List;

public class UserManager implements IUserManagement {
    private final List<User> users;

    public UserManager() {
        users = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        if(!doesUserExist(user.getName()))
            users.add(user);
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }
    @Override
    public void removeUser(String name) {
        if(!doesUserExist(name))
            return;
        User user = getUserByName(name);
        users.remove(user);
    }
    @Override
    public void removeDisconnectedUsers(){
        for (User user : users) {
            if(user.getStatus()== UserStatus.DISCONNECTED)
                removeUser(user);
        }
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
    public List<User> getAllUsers() {
        return users;
    }
}
