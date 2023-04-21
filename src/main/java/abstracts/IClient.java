package abstracts;

import models.Message;
import utils.MessageType;

import java.util.ArrayList;

public interface IClient {
    void login(String nickname);
    void logout();
    void sendMessage(String message);



}
