package com.getjavajob.training.yakovleva.service.junit;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.service.MessageService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class MessageServiceTest {

    @Test
    void getWallMassageAccount() {
        Account account = Mockito.mock(Account.class);
        List<Message> messages = Mockito.mock(List.class);
        MessageService messageService = Mockito.mock(MessageService.class);
//        Mockito.when(messageService.getWallMassageAccount(account)).thenReturn(messages);
//        Assert.assertEquals(messages, messageService.getWallMassageAccount(account));
    }

    @Test
    void getMessage() {
        Account account = Mockito.mock(Account.class);
        List<Message> messages = Mockito.mock(List.class);
        MessageService messageService = Mockito.mock(MessageService.class);
//        Mockito.when(messageService.getMessages(account)).thenReturn(messages);
//        Assert.assertEquals(messages, messageService.getMessages(account));
    }

    @Test
    void getUniqueMessages() {
        Account account = Mockito.mock(Account.class);
        List<Message> messages = Mockito.mock(List.class);
        MessageService messageService = Mockito.mock(MessageService.class);
//        Mockito.when(messageService.getUniqueMessages(account)).thenReturn(messages);
//        Assert.assertEquals(messages, messageService.getUniqueMessages(account));
    }

    @Test
    void getAccountMessage() {
        Account account = Mockito.mock(Account.class);
        List<Message> messages = Mockito.mock(List.class);
        MessageService messageService = Mockito.mock(MessageService.class);
//        Mockito.when(messageService.getUniqueMessages(account)).thenReturn(messages);
//        Assert.assertEquals(messages, messageService.getUniqueMessages(account));
    }

    @Test
    void createMassageTrue() {
        Message message = Mockito.mock(Message.class);
        MessageService messageService = Mockito.mock(MessageService.class);
//        Mockito.when(messageService.createMassage(message)).thenReturn(true);
//        Assert.assertEquals(true, messageService.createMassage(message));
    }

    @Test
    void createMassageFalse() {
        Message message = Mockito.mock(Message.class);
        MessageService messageService = Mockito.mock(MessageService.class);
//        Mockito.when(messageService.createMassage(message)).thenReturn(false);
//        Assert.assertEquals(false, messageService.createMassage(message));
    }

    @Test
    void deleteTrue() {
        MessageService messageService = Mockito.mock(MessageService.class);
        Mockito.when(messageService.delete(1)).thenReturn(true);
        Assert.assertEquals(true, messageService.delete(1));
    }

    @Test
    void deleteFalse() {
        MessageService messageService = Mockito.mock(MessageService.class);
        Mockito.when(messageService.delete(1)).thenReturn(false);
        Assert.assertEquals(false, messageService.delete(1));
    }

}