package controllers;

import abstracts.IMessageListener;
import models.Message;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

import static utils.Constants.MAX_MESSAGE_LENGTH;

public class MessageReceiver extends Thread {
    private final MulticastSocket socket;
    private final IMessageListener messageListener;


    public MessageReceiver(MulticastSocket socket, IMessageListener messageListener) {
        this.socket = socket;
        this.messageListener = messageListener;
    }


    @Override
    public void run() {
        byte[] buffer = new byte[MAX_MESSAGE_LENGTH];
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
                    interrupt();
                    break;
            }
        }
    }


}
