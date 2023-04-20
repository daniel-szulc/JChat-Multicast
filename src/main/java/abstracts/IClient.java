package abstracts;

import java.util.ArrayList;

public interface IClient {
    void login(String nickname);
    void logout();
    void sendMessage(String message);
    void forceExit();
    void updateUsersList(ArrayList<String> users);
    void receiveMessage(String message, String sender);
    void setConnected(boolean connected);
    String getNickname();
    void setNickname(String nickname);
    boolean isConnected();
}
