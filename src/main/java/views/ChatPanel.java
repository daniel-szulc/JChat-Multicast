package views;

import abstracts.IChatPanel;
import abstracts.IChatMember;
import models.User;
import utils.MessageType;
import utils.UserStatus;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatPanel extends JFrame implements IChatPanel {

    DefaultListModel<String> messagesListModel;
    DefaultListModel<String> usersListModel;

    IChatMember client;

    public ChatPanel(IChatMember client) {
        this.client = client;
        setContentPane(mainPanel);
        setSize(450, 300);
        setTitle("Chat: " + client.getUsername());
        setLocationRelativeTo(null);
        setVisible(true);
        sendBtn.setVisible(true);
        messagesListModel = new DefaultListModel<>();
        messages.setModel(messagesListModel);

        usersListModel = new DefaultListModel<>();
        usersList.setModel(usersListModel);


        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        message.addActionListener(e -> sendMessage());

        sendBtn.addActionListener(e -> sendMessage());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                setVisible(false);
                client.logout();
                dispose();
            }
        });
    }

    public void sendMessage() {
        receiveMessage(message.getText(), "You", new Date());
        client.sendMessage(message.getText());
        message.setText("");
    }

    private String formatTime(Date timestamp) {
        LocalTime time = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void receiveMessage(String message, String sender, Date timestamp) {
        String messageText = String.format("[%s] %s> %s", formatTime(timestamp), sender, message);
        messagesListModel.addElement(messageText);
    }

    public void receiveMessage(MessageType type, String username, Date timestamp) {
        String message;
        switch (type) {
            case LOGIN -> message = String.format("User '%s' has joined the chat group.", username);
            case LOGOUT -> message = String.format("User '%s' has left the chat group.", username);
            default -> {
                return;
            }
        }
        String messageText = String.format("[%s] %s", formatTime(timestamp), message);
        messagesListModel.addElement(messageText);
        messages.revalidate();
        messages.repaint();
    }

    @Override
    public void updateUsersList(List<User> users) {
            List<String> userNames = new ArrayList<>();
                for (User user : users) {
            userNames.add(user.getName() + (client.getUsername().equals(user.getName()) ? " (You)" : (user.getStatus().equals(UserStatus.INACTIVE) ? " (INACTIVE)" : "")));
        }
        usersListModel = new DefaultListModel<>();
        if (userNames.size() > 0)
            usersListModel.addAll(userNames);
        usersList.setModel(usersListModel);
        usersList.revalidate();
        usersList.repaint();

}

    private JButton sendBtn;
    private JPanel mainPanel;
    private JTextField message;
    private JList<String> messages;
    private JList<String> usersList;
}
