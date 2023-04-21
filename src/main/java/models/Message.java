package models;

import utils.MessageType;

import java.io.Serializable;
import java.util.Date;


public class Message implements Serializable {
    private String sender;
    private String receiver;
    private String content;
    private Date timestamp;
    private MessageType messageType;

    public Message(MessageType messageType, String sender, String content, String receiver) {
        this(messageType, sender, content);
        this.receiver = receiver;
    }

    public Message(MessageType messageType, String sender, String content) {
        this(messageType, sender);
        this.content = content;
    }

    public Message(MessageType messageType, String sender) {
        this.messageType = messageType;
        this.sender = sender;
        content = null;
        receiver = null;
        this.timestamp = new Date();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public MessageType getMessageType() {
        return messageType;
    }

}
