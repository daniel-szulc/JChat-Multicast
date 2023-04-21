package controllers;

import abstracts.IChatPanel;
import abstracts.IClient;
import abstracts.ILoginPanel;
import models.User;
import models.Message;
import models.UserManager;
import utils.MessageType;
import utils.UserStatus;
import views.LoginPanel;

import static java.lang.Thread.sleep;
import static utils.Constants.*;

import java.io.*;
import java.net.*;

public class Client implements IClient {
    private MulticastSocket socket;
    private InetSocketAddress group;
    private User user;
    private UserManager userManager;
    private IChatPanel chatPanel;
    private ILoginPanel loginPanel;
    Thread waitingThread; //a thread that checks if the join request been granted or not

    public Client() throws IOException {
        socket = new MulticastSocket(PORT);
        userManager = new UserManager();
        loginPanel = new LoginPanel(this);
        receiveMessages();
    }

    @Override
    public void login(String username) {
        user = new User(username);
        try {
            group = new InetSocketAddress(InetAddress.getByName(MULTICAST_ADDRESS), PORT);
            NetworkInterface netIf = NetworkInterface.getByName(NETWORK_INTERFACE);
            this.socket.joinGroup(group, netIf);

            sendMessage(MessageType.LOGIN_REQUEST);

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

        //chatPanel = new ChatPanel(this);

        String message = String.format("User '%s' has joined the chat group.", user.getName());
        sendMessage(message);
        sendMessage(MessageType.LOGIN);

    }


    @Override
    public void sendMessage(String content) {
        Message message = new Message(MessageType.MESSAGE, user.getName(), content);
        sendMessage(message);
    }

    public void sendMessage(MessageType messageType) {
        Message message = new Message(messageType, user.getName());
        sendMessage(message);
    }

    public void sendMessage(Message message) {
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(message);

            byte[] messageBytes = bos.toByteArray();

            DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, InetAddress.getByName(MULTICAST_ADDRESS), PORT);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMessages() {
        byte[] buffer = new byte[1024];
        DatagramPacket incomingPacket = new DatagramPacket(buffer, buffer.length);

        while (true) {
            try {
                socket.receive(incomingPacket);
                byte[] bytes = incomingPacket.getData();

                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bis);

                Message message = (Message) ois.readObject();

                System.out.println(message.getMessageType());
                System.out.println(message.getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void logout() {

    }



}

