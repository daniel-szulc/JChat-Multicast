package views;

import abstracts.IClient;
import abstracts.ILoginPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginPanel extends JFrame implements ILoginPanel {


    public LoginPanel(IClient client) {
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
    }

    private JTextField nicknameField;
    private JButton joinButton;
    private JPanel loginPanel;
    private JLabel infoLabel;

    @Override
    public void displayInformation(String information) {
        System.out.println("Login panel info: " + information);
        infoLabel.setText(information);
        joinButton.setEnabled(true);
    }

    @Override
    public void setNickname(String nickname) {
        nicknameField.setText(nickname);
    }
    @Override
    public void handleLogin(IClient client) {
        String nickname = nicknameField.getText();
        client.login(nickname);
        joinButton.setEnabled(false);
    }

    @Override
    public void exitPanel(){
        setVisible(false);
        dispose();
    }
}
