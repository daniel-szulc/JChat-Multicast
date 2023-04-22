package abstracts;

import models.User;

import java.util.List;

public interface IUserManagement {
    void addUser(User user);
    void removeUser(User user);
    void removeUser(String name);
    boolean doesUserExist(String name);
    User getUserByClientId(String clientId);
    User getUserByName(String name);
    List<User> getAllUsers();
    void removeDisconnectedUsers();
}
