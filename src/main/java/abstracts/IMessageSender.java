package abstracts;

import models.Message;
import utils.MessageType;

public interface IMessageSender {
    void sendMessage(String content);
    void sendMessage(MessageType messageType);
    void sendMessage(MessageType messageType, String receiverId);
    void sendMessage(Message message);

}
