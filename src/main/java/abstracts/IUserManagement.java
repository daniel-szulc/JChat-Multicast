package abstracts;

import models.User;

import java.util.List;

public interface IUserManagement {
    void addUser(User user);
    void removeUser(User user);
    boolean doesUserExist(String name);
    User getUserByClientId(String clientId);
    User getUserByName(String name);
    void removeUserByName(String name);
    List<User> getAllUsers();
}
