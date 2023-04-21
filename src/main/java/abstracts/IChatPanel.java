package abstracts;

import java.util.ArrayList;

public interface IChatPanel {
    void addMessage(String message, String sender);
    void setUsersList(ArrayList<String> users);
}
