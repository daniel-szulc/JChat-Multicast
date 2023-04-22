package controllers;

import abstracts.IMessageSender;
import models.Message;
import models.User;
import utils.MessageType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import static utils.Constants.MULTICAST_ADDRESS;
import static utils.Constants.PORT;

public class MessageSender implements IMessageSender {
    private final MulticastSocket socket;
    private final String clientId;
    private final String username;

    public MessageSender(MulticastSocket socket, User client) {
        this.socket = socket;
        clientId = client.getClientId();
        username = client.getName();
    }

    @Override
    public void sendMessage(String content) {
        Message message = new Message(MessageType.MESSAGE, clientId, content);
        sendMessage(message);
    }
    @Override
    public void sendMessage(MessageType messageType) {
        Message message = new Message(messageType, clientId, username);
        sendMessage(message);
    }
    @Override
    public void sendMessage(MessageType messageType, String receiverId) {
        Message message = new Message(messageType, clientId, username ,receiverId);
        sendMessage(message);
    }
    @Override
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
}

