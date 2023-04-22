package controllers;

import abstracts.IChatMember;
import abstracts.IUserManagement;
import models.Message;
import models.User;
import models.UserManager;
import utils.MessageType;
import utils.UserStatus;
import views.ChatPanel;

import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Objects;

import static utils.Constants.MAX_USERS;
import static utils.Constants.NETWORK_INTERFACE;

public class Chat extends Client implements IChatMember {

    private final IUserManagement userManager;


    @Override
    public IUserManagement getUserManager() {
        return userManager;
    }

    public Chat() throws IOException {
        super();
        userManager = new UserManager();
    }

    @Override
    protected void join(){
        super.join();
        user.setStatus(UserStatus.ACTIVE);
        if(loginPanel!=null) {
            loginPanel.disposePanel();
        }

        if(!userManager.doesUserExist(user.getName()))
            userManager.addUser(user);

        if (chatPanel!=null)
            return;

        chatPanel = new ChatPanel(this);
        messageSender.sendMessage(MessageType.LOGIN);
        sessionMonitor = new UserSessionMonitor( this);
        sessionMonitor.start();
    }

    @Override
    public void logout() {
        try {
            sendMessage(MessageType.LOGOUT);
            sessionMonitor.interrupt();
            messageReceiver.interrupt();
            NetworkInterface netIf = NetworkInterface.getByName(NETWORK_INTERFACE);
            if(getSocket().isConnected())
                getSocket().leaveGroup(getGroup(), netIf);

            getSocket().close();
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLoginRequest(String username, String clientId) {

        if (userManager.doesUserExist(username)) {
            if (Objects.equals(userManager.getUserByName(username).getClientId(), clientId))  //when the same user is already on the list
            {
                messageSender.sendMessage(MessageType.LOGIN_SUCCESS, clientId);
            } else {
                messageSender.sendMessage(MessageType.LOGIN_FAILURE_NICKNAME_TAKEN, clientId);   //In case the username already exists with someone else
            }
            return;
        }

        if (userManager.getAllUsers().size() >= MAX_USERS) {
            messageSender.sendMessage(MessageType.LOGIN_FAILURE_MAX_USERS_REACHED, clientId);
            return;
        }

        messageSender.sendMessage(MessageType.LOGIN_SUCCESS, clientId);  //otherwise accept the user request
    }


    @Override
    public void sendMessage(String message) {
        messageSender.sendMessage(message);
    }

    @Override
    public void sendMessage(MessageType messageType) {
        messageSender.sendMessage(messageType);
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public void updateUsersList() {

        userManager.removeDisconnectedUsers();
        if(chatPanel!=null)
             chatPanel.updateUsersList(userManager.getAllUsers());
    }

    public void onMessageReceived(Message message) {
        if(Objects.equals(message.getSender(), user.getClientId())) //Prevent processing of your own messages
        {
            return;
        }
        super.onMessageReceived(message);
        if(user.getStatus()==UserStatus.DISCONNECTED || user.getStatus()==UserStatus.REJECTED)
            return;

        switch (message.getMessageType()) {
            case MESSAGE -> {
                if(chatPanel!=null) {
                    String name = userManager.getUserByClientId(message.getSender()).getName();
                    chatPanel.receiveMessage(message.getContent(), name, message.getTimestamp());
                }
            }
            case LOGIN_REQUEST -> handleLoginRequest(message.getContent(), message.getSender());
            case LOGIN -> {
                userManager.addUser(new User(message.getContent(), message.getSender()));
                handleLoginRequest(message.getContent(), message.getSender());
                messageSender.sendMessage(MessageType.ACTIVITY_CONFIRMATION);
                updateUsersList();
                chatPanel.receiveMessage(message.getMessageType(), message.getContent(), message.getTimestamp());
            }
            case LOGOUT -> {
                userManager.removeUser(message.getContent());
                chatPanel.receiveMessage(message.getMessageType(), message.getContent(), message.getTimestamp());
                updateUsersList();
            }
            case ACTIVITY_CONFIRMATION -> {
                userManager.addUser(new User(message.getContent(), message.getSender()));
                userManager.getUserByClientId(message.getSender()).setLastActivity(System.currentTimeMillis());
                updateUsersList();
            }
        }
    }

}
