package com.getjavajob.training.yakovleva.common;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageTest {
    private final int expectedInt = 1;
    private final String expectedString = "test";
    private final Date expectedDate = new Date();
    private final boolean expectedBool = false;

    @Test
    void getId() {
        Message message = new Message();
        message.setId(expectedInt);
        assertEquals(expectedInt, message.getId());
    }

    @Test
    void setId() {
        Message message = new Message();
        message.setId(expectedInt);
        assertEquals(expectedInt, message.getId());
    }

    @Test
    void getSenderId() {
        Message message = new Message();
        message.setSenderId(expectedInt);
        assertEquals(expectedInt, message.getSenderId());
    }

    @Test
    void setSenderId() {
        Message message = new Message();
        message.setSenderId(expectedInt);
        assertEquals(expectedInt, message.getSenderId());
    }

    @Test
    void getMessage() {
        Message message = new Message();
        message.setMessage(expectedString);
        assertEquals(expectedString, message.getMessage());
    }

    @Test
    void setMessage() {
        Message message = new Message();
        message.setMessage(expectedString);
        assertEquals(expectedString, message.getMessage());
    }

    @Test
    void getPicture() {
        Message message = new Message();
        message.setPicture(expectedString);
        assertEquals(expectedString, message.getPicture());
    }

    @Test
    void setPicture() {
        Message message = new Message();
        message.setPicture(expectedString);
        assertEquals(expectedString, message.getPicture());
    }

    @Test
    void getPublicationDate() {
        Message message = new Message();
        message.setId(expectedInt);
        assertEquals(expectedInt, message.getId());
    }

    @Test
    void setPublicationDate() {
        Message message = new Message();
        message.setPublicationDate(expectedDate);
        assertEquals(expectedDate, message.getPublicationDate());
    }

    @Test
    void isEdited() {
        Message message = new Message();
        message.setEdited(expectedBool);
        assertEquals(expectedBool, message.isEdited());
    }

    @Test
    void setEdited() {
        Message message = new Message();
        message.setEdited(expectedBool);
        assertEquals(expectedBool, message.isEdited());
    }

}