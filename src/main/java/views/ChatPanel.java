package views;

import abstracts.IChatPanel;
import abstracts.IChatMember;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class ChatPanel extends JFrame implements IChatPanel {

    DefaultListModel<String> messagesListModel;
    DefaultListModel<String> usersListModel;
    public ChatPanel(IChatMember client){
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

        message.addActionListener(e -> sendMessage(client));

        sendBtn.addActionListener(e -> sendMessage(client));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                client.logout();
                dispose();
            }
        });
    }

    public void sendMessage(IChatMember client){
        receiveMessage(message.getText(), "You");
        client.sendMessage(message.getText());
        message.setText("");
    }

    public void receiveMessage(String message, String sender){
        messagesListModel.addElement(sender + "> " + message);
    }


    public void setUsersList(ArrayList<String> users){
        usersListModel.clear();
        usersListModel.addAll(users);
    }




    private JButton sendBtn;
    private JPanel mainPanel;
    private JTextField message;
    private JList<String> messages;
    private JList<String> usersList;
    private JButton forceExit;
}
