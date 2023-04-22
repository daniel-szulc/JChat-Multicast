package views;

import abstracts.IClient;
import abstracts.ILoginPanel;
import utils.MessageType;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginPanel extends JFrame implements ILoginPanel {


    public LoginPanel(IClient client) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
        }
        setContentPane(loginPanel);
        this.setTitle("Chat");
        this.setSize(250, 300);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        nicknameField.addActionListener(e -> handleLogin(client));
        joinButton.addActionListener(e -> handleLogin(client));

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                dispose();
            }
        });
        SwingUtilities.updateComponentTreeUI(this);
    }

    private JTextField nicknameField;
    private JButton joinButton;
    private JPanel loginPanel;
    private JLabel infoLabel;

    @Override
    public void displayInformation(MessageType information) {
        String informationString = "";
        switch (information) {
            case LOGIN_FAILURE_NICKNAME_TAKEN -> informationString = "Nickname is already taken";
            case LOGIN_FAILURE_MAX_USERS_REACHED -> informationString = "Max users reached";
        }
        infoLabel.setText(informationString);
        joinButton.setEnabled(true);
        nicknameField.setEnabled(true);
    }

    @Override
    public void handleLogin(IClient client) {
        String nickname = nicknameField.getText();
        if(nickname.length() == 0){
            infoLabel.setText("Enter your username!");
            return;
        }
        client.login(nickname);
        joinButton.setEnabled(false);
        nicknameField.setEnabled(false);
    }

    @Override
    public void disposePanel(){
        setVisible(false);
        dispose();
    }
}
