package abstracts;

public interface ILoginPanel {
    void displayInformation(String message);
    void setNickname(String nickname);
    void handleLogin(IClient client);
    void exitPanel();
}
