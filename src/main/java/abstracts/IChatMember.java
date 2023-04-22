package abstracts;

import utils.MessageType;

public interface IChatMember {
    void sendMessage(String message);
    void sendMessage(MessageType messageType);
    String getUsername();
    void updateUsersList();
    IUserManagement getUserManager();
    void logout();
}
