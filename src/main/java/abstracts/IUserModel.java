package abstracts;

import models.User;

import java.util.List;

public interface IUserModel {
    void addUser(User user);
    void removeUser(User user);
    User getUserByClientId(String clientId);
    List<User> getAllUsers();
}
