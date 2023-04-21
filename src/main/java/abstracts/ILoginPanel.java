package abstracts;

public interface ILoginPanel {
    void setInfoMessage(String message);
    void setNickname(String nickname);
    void handleLogin(IClient client);
    void exitPanel();
}
