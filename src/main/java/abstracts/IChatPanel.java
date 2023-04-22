package abstracts;

import models.User;
import utils.MessageType;

import java.util.Date;
import java.util.List;

public interface IChatPanel {
    void receiveMessage(String message, String sender, Date timestamp);
    void updateUsersList(List<User> users);

    void dispose();

    void receiveMessage(MessageType messageType, String sender, Date timestamp);
}
