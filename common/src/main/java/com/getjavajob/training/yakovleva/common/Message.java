package com.getjavajob.training.yakovleva.common;

import com.getjavajob.training.yakovleva.common.utilsEnum.MessageType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "message")
public class Message extends Common implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "sender_id")
    private int senderId;
    @Column(name = "message")
    private String message;
    @Lob
    @Column(name = "picture")
    private String picture;
    @Column(name = "publication_date")
    private Date publicationDate;
    @Column(name = "edited")
    private boolean edited;
    @Column(name = "message_type")
    private MessageType messageType;
    @Column(name = "receiver_id")
    private int receiverId;
    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, insertable = false, name = "receiver_id", referencedColumnName = "account_id")
    private Account account;

    public Message(int id, int senderId, String message, String picture, Date publicationDate,
                   boolean edited, MessageType messageType, int receiverId) {
        this.id = id;
        this.senderId = senderId;
        this.message = message;
        this.picture = picture;
        this.publicationDate = publicationDate;
        this.edited = edited;
        this.messageType = messageType;
        this.receiverId = receiverId;
    }

    public Message() {
    }

    public Message(int senderId, String message, String picture, Date publicationDate,
                   boolean edited) {
        this.senderId = senderId;
        this.message = message;
        this.picture = picture;
        this.publicationDate = publicationDate;
        this.edited = edited;
    }

    public Message(int id, int senderId, String message, String picture, Date publicationDate,
                   boolean edited) {
        this.id = id;
        this.senderId = senderId;
        this.message = message;
        this.picture = picture;
        this.publicationDate = publicationDate;
        this.edited = edited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", messageType=" + messageType +
                ", publicationDate=" + publicationDate +
                ", message='" + message + '\'' +
                ", edited=" + edited +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return id == message1.id && senderId == message1.senderId && edited == message1.edited && receiverId == message1.receiverId && Objects.equals(message, message1.message) && Objects.equals(picture, message1.picture) && Objects.equals(publicationDate, message1.publicationDate) && messageType == message1.messageType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderId, message, picture, publicationDate, edited, messageType, receiverId);
    }

}