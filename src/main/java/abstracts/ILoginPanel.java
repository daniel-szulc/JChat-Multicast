package abstracts;

import utils.MessageType;

public interface ILoginPanel {
    void displayInformation(MessageType message);
    void handleLogin(IClient client);
    void disposePanel();

    void setVisible(boolean visible);
}
