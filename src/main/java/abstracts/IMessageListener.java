package abstracts;

import models.Message;

public interface IMessageListener {
    void onMessageReceived(Message message);
}
