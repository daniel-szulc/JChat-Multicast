package controllers;

import abstracts.*;
import models.User;
import models.Message;
import utils.MessageType;
import utils.UserStatus;
import views.LoginPanel;

import static java.lang.Thread.sleep;
import static utils.Constants.*;

import java.io.*;
import java.net.*;
import java.util.Objects;

public class Client implements IClient, IMessageListener {

    private final MulticastSocket socket;
    private InetSocketAddress group;
    protected User user;
    protected IChatPanel chatPanel;
    protected ILoginPanel loginPanel;
    IMessageSender messageSender;
    Thread waitingThread; //a thread that checks if the join request been granted or not
    MessageReceiver messageReceiver;
    UserSessionMonitor sessionMonitor;

    public MulticastSocket getSocket() {
        return socket;
    }

    public InetSocketAddress getGroup() {
        return group;
    }

    public Client() throws IOException {
        socket = new MulticastSocket(PORT);
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

            messageSender = new MessageSender(socket, user);

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
                    join();
                }
            } catch (InterruptedException ignored) {
            }
        });
        waitingThread.start();
    }

    protected void join(){}

    protected void reject(MessageType messageType){
        if(waitingThread.isAlive())
             waitingThread.interrupt();

            if(chatPanel!=null) {
                chatPanel.dispose();
                chatPanel = null;
            }
            if(loginPanel==null)
                loginPanel = new LoginPanel(this);
            loginPanel.setVisible(true);
            loginPanel.displayInformation(messageType);
            user.setStatus(UserStatus.REJECTED);
            try {
                NetworkInterface netIf = NetworkInterface.getByName(NETWORK_INTERFACE);
                socket.leaveGroup(group, netIf);
            } catch (IOException e) {
                //ignore
            }
            if (waitingThread!=null)
                waitingThread.interrupt();
    }

    @Override
    public void onMessageReceived(Message message) {
        if(Objects.equals(message.getSender(), user.getClientId())) //Prevent processing of your own messages
        {
            return;
        }

        if(Objects.equals(message.getReceiver(), user.getClientId())) {
            if (message.getMessageType() == MessageType.LOGIN_SUCCESS)
            {
                waitingThread.interrupt();
                join();
            }
            else if (message.getMessageType() == MessageType.LOGIN_FAILURE_MAX_USERS_REACHED || message.getMessageType() == MessageType.LOGIN_FAILURE_NICKNAME_TAKEN)
            {
                reject(message.getMessageType());
            }
        }
    }




}

