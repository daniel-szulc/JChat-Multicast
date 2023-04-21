package controllers;

import abstracts.IMessageListener;
import models.Message;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class MessageReceiver {
    private MulticastSocket socket;
    private IMessageListener messageListener;

    public MessageReceiver(MulticastSocket socket, IMessageListener messageListener) {
        this.socket = socket;
        this.messageListener = messageListener;
    }

    public void start() {
        byte[] buffer = new byte[1024];
        DatagramPacket incomingPacket = new DatagramPacket(buffer, buffer.length);

        while (true) {
            try {
                socket.receive(incomingPacket);
                byte[] bytes = incomingPacket.getData();

                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bis);

                Message message = (Message) ois.readObject();

                messageListener.onMessageReceived(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
