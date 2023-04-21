package controllers;

import models.Message;
import utils.MessageType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import static utils.Constants.MULTICAST_ADDRESS;
import static utils.Constants.PORT;

public class MessageSender {
    private MulticastSocket socket;
    private String senderName;

    public MessageSender(MulticastSocket socket, String username) {
        this.socket = socket;
        senderName = username;
    }

    public void sendMessage(String content) {
        Message message = new Message(MessageType.MESSAGE, senderName, content);
        sendMessage(message);
    }

    public void sendMessage(MessageType messageType) {
        Message message = new Message(messageType, senderName);
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
}

