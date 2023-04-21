package abstracts;

public interface IChatMember {
    void sendMessage(String message);
    String getUsername();
    void logout();
}
