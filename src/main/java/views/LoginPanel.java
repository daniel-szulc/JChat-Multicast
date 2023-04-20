package views;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginPanel  extends JFrame{


    public LoginPanel() {
        setContentPane(loginPanel);
        this.setTitle("Chat");
        this.setSize(250, 300);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

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
}
