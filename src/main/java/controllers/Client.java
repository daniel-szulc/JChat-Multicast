package controllers;

import abstracts.*;
import models.User;
import models.Message;
import models.UserManager;
import utils.MessageType;
import utils.UserStatus;
import views.ChatPanel;
import views.LoginPanel;

import static java.lang.Thread.sleep;
import static utils.Constants.*;

import java.io.*;
import java.net.*;

public class Client implements IClient, IChatMember, IMessageListener {
    private MulticastSocket socket;
    private InetSocketAddress group;
    private User user;
    private UserManager userManager;
    private IChatPanel chatPanel;
    private ILoginPanel loginPanel;
    Thread waitingThread; //a thread that checks if the join request been granted or not

    MessageReceiver messageReceiver;
    MessageSender messageSender;

    public Client() throws IOException {
        socket = new MulticastSocket(PORT);
        userManager = new UserManager();
        loginPanel = new LoginPanel(this);

        messageReceiver = new MessageReceiver(socket,this);
        messageReceiver.start();
    }

    @Override
    public void login(String username) {
        user = new User(username);
        try {
            group = new InetSocketAddress(InetAddress.getByName(MULTICAST_ADDRESS), PORT);
            NetworkInterface netIf = NetworkInterface.getByName(NETWORK_INTERFACE);
            this.socket.joinGroup(group, netIf);

            messageSender = new MessageSender(socket, username);
            messageSender.sendMessage(MessageType.LOGIN_REQUEST);

            waitingForResponseToJoin();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitingForResponseToJoin() {
        waitingThread = new Thread(() -> {
            if(chatPanel!=null)
                return;
            try {
                sleep(3000);
                if(chatPanel==null && user.getStatus()!= UserStatus.REJECTED) {
                    joinChat();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        waitingThread.start();
    }

    void joinChat(){

        user.setStatus(UserStatus.ACTIVE);
        if(loginPanel!=null) {
            loginPanel.exitPanel();
        }

        if(!userManager.doesUserExist(user.getName()))
            userManager.addUser(user);

        if (chatPanel!=null)
            return;

        chatPanel = new ChatPanel(this);

        String message = String.format("User '%s' has joined the chat group.", user.getName());
        sendMessage(message);
        messageSender.sendMessage(MessageType.LOGIN);
    }

    @Override
    public void onMessageReceived(Message message) {
        if(chatPanel!=null)
             chatPanel.receiveMessage(message.getContent(), message.getSender());
    }

    @Override
    public void logout() {

    }

    @Override
    public void sendMessage(String message) {
        messageSender.sendMessage(message);
    }

    @Override
    public String getUsername() {
        return user.getName();
    }


}

