package com.getjavajob.training.yakovleva.repository;

import com.getjavajob.training.yakovleva.config.DaoConfig;
import config.DaoConfigTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class, DaoConfigTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
class AccountMessageRepositoryTest {/*
    @Autowired
    private AccountMessageRepository accountMessageRepository;
    private MessageRepository messageRepository;
    private AccountRepository accountRepository;
    private String testString = "test";
    private int testInt = 1;
    private MessageType expectedMessageType = MessageType.GROUP;
    private boolean expectedBoolean = false;
    private Date expectedDate = new Date();

    @BeforeEach
    void createTestData() {
        Phone phone = new Phone();
        phone.setPhoneNumber(testString);
        phone.setPhoneType(1);
        List<Phone> phones = new ArrayList<>();

        AccountDetails accountDetails = new AccountDetails();


        Account account = new Account();
        account.setUsername(testString);
        account.setPassword(testString);
        account.setRole(Role.ROLE_ADMIN);
        account.setPhones(phones);
        accountRepository.save(account);


        Message message = new Message();
        message.setEdited(expectedBoolean);
        message.setPicture(testString);
        message.setId(account.getId());
        message.setPublicationDate(expectedDate);
        message.setSenderId(testInt);
        message.setMessage(testString);
        messageRepository.save(message);

        AccountMessage accountMessage = new AccountMessage();
        accountMessage.setMessageType(expectedMessageType);
        accountMessage.setMessageId(testInt);
        accountMessage.setReceiverId(account.getId());
//        accountMessage.setMessage(message);
        accountMessageRepository.save(accountMessage);
    }

    @Test
    void findAllByMessageIdAndMessageType() {
        List<AccountMessage> accountMessages = new ArrayList<>();
        System.out.println(accountMessageRepository.get(1));
    }

    @Test
    void getAccountMessagesByReceiverIdAndMessageType() {
    }

    @Test
    void getAccountMessageByMessageId() {
    }

    @Test
    void get() {
    }

    @Test
    void testGet() {
    }

    @Test
    void getAccountMessageByReceiverIdAndMessageType() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteByMessageId() {
    }

    @Test
    void getMessages() {
    }
    */
}