package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.utilsEnum.Role;
import com.getjavajob.training.yakovleva.config.DaoConfig;
import config.DaoConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class, DaoConfigTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
class MessageDaoTest {
    @Autowired
    private MessageDao messageDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private GroupDao groupDao;
    private String testString = "test";
    private int testInt = 1;
    private Date testDate = new Date(0);
    private boolean testBoolean = true;

    @BeforeEach
    void createTestData() {
        Account account = new Account();
        account.setRole(Role.ROLE_USER);
        account.setPassword(testString);
        account.setUsername(testString);
//        account.setName(testString);
//        account.setLastName(testString);
//        account.setSurname(testString);
        accountDao.create(account);
        Group group = new Group();
        group.setInfo(testString);
        group.setGroupName(testString);
//        group.setLogo(testString);
        group.setIdGroupCreator(testInt);
        group.setGroupId(testInt);
        groupDao.create(group);
        for (int i = 0; i < 3; i++) {
            Message message = new Message();
            message.setMessage(testString);
//            message.setMessageType(i);
//            message.setUsernameSender(testString);
//            message.setUsernameReceiving(testString);
//            message.setReceiverId(testInt);
            message.setSenderId(testInt);
            message.setPublicationDate(testDate);
//            messageDao.setPicture(testString);
            message.setEdited(testBoolean);
            messageDao.create(message);
        }
        List<Message> messages = messageDao.getAllMessages();
    }

    @Test
    void create() {
        Message message = new Message();
        message.setMessage(testString);
//        message.setMessageType(testInt);
//        message.setUsernameSender(testString);
//        message.setUsernameReceiving(testString);
//        message.setReceiverId(testInt);
        message.setSenderId(testInt);
        message.setPublicationDate(testDate);
//        message.setPicture(testString);
        message.setEdited(testBoolean);
        boolean actual = messageDao.create(message);
        assertEquals(testBoolean, actual);
    }

    @Test
    void getMessageUserId() {
        Message message = new Message();
        message.setMessage(testString);
//        message.setMessageType(testInt);
//        message.setUsernameSender(testString);
//        message.setUsernameReceiving(testString);
//        message.setReceiverId(testInt);
        message.setSenderId(testInt);
        message.setPublicationDate(testDate);
//        message.setPicture(testString);
        message.setEdited(testBoolean);
        message.setId(2);
        List<Message> expected = new ArrayList<>();
        expected.add(message);
        List<Message> actual = messageDao.getMessageUserId(1);
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }

    @Test
    void getMessagesByReceiverId() {
        Message message = new Message();
        message.setMessage(testString);
//        message.setMessageType(1);
//        message.setUsernameSender(testString);
//        message.setUsernameReceiving(testString);
//        message.setReceiverId(testInt);
        message.setSenderId(testInt);
        message.setPublicationDate(testDate);
//        message.setPicture(testString);
        message.setEdited(testBoolean);
        message.setId(3);
        List<Message> expected = new ArrayList<>();
        expected.add(message);
        List<Message> actual = messageDao.getMessagesBy(1);
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }

    @Test
    void getUniqueMessagesForUser() {
        Message message = new Message();
        message.setMessage(testString);
//        message.setMessageType(1);
//        message.setUsernameSender(testString);
//        message.setUsernameReceiving(testString);
//        message.setReceiverId(testInt);
        message.setSenderId(testInt);
        message.setPublicationDate(testDate);
//        message.setPicture(testString);
        message.setEdited(testBoolean);
        message.setId(3);
        List<Message> expected = new ArrayList<>();
        expected.add(message);
        List<Message> actual = messageDao.getUniqueMessagesForUser(1);
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }

    @Test
    void getGroupMessage() {
        Message message = new Message();
        message.setMessage(testString);
//        message.setMessageType(1);
//        message.setUsernameSender(testString);
//        message.setUsernameReceiving(testString);
//        message.setReceiverId(testInt);
        message.setSenderId(testInt);
        message.setPublicationDate(testDate);
//        message.setPicture(testString);
        message.setEdited(testBoolean);
        message.setId(4);
        List<Message> expected = new ArrayList<>();
        expected.add(message);
        List<Message> actual = messageDao.getGroupMessage(1);
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }

    @Test
    void getMessageAccounts() {
        Message message = new Message();
        message.setMessage(testString);
//        message.setMessageType(1);
//        message.setUsernameSender(testString);
//        message.setUsernameReceiving(testString);
//        message.setReceiverId(testInt);
        message.setSenderId(testInt);
        message.setPublicationDate(testDate);
//        message.setPicture(testString);
        message.setEdited(testBoolean);
        message.setId(3);
        List<Message> expected = new ArrayList<>();
        expected.add(message);
        List<Message> actual = messageDao.getMessageAccounts(1, 1);
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }

    @Test
    void getWallMessage() {
        Message message = new Message();
        message.setMessage(testString);
//        message.setMessageType(0);
//        message.setUsernameSender(testString);
//        message.setUsernameReceiving(testString);
//        message.setReceiverId(testInt);
        message.setSenderId(testInt);
        message.setPublicationDate(testDate);
//        message.setPicture(testString);
        message.setEdited(testBoolean);
        message.setId(3);
        List<Message> expected = new ArrayList<>();
        expected.add(message);
        List<Message> actual = messageDao.getMessageAccounts(1, 1);
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
    }

    @Test
    void getAllMessages() {
        List<Message> actual = messageDao.getAllMessages();
        assertEquals(3, actual.size());
    }

    @Test
    void delete() {
        boolean actual = messageDao.delete(2);
        assertEquals(true, actual);
    }

}