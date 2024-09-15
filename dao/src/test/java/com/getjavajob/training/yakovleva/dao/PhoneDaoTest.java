package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Phone;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class, DaoConfigTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
class PhoneDaoTest {
    @Autowired
    private PhoneDao phoneDao;
    @Autowired
    private AccountDao accountDao;
    private String testString = "test";
    private int testInt = 1;

    @BeforeEach
    void createTestData() {
        Account account = new Account();
        account.setUsername(testString);
//        account.setName(testString);
//        account.setPassword(testString);
//        account.setSurname(testString);
//        account.setLastName(testString);
//        account.setRole(testInt);
//        account.setAddressJob(testString);
//        account.setAddressHome(testString);
//        account.setPhotoFileName(testString);
//        account.setEmail(testString);
//        account.setAboutMe(testString);
//        account.setIcq(testInt);
        accountDao.create(account);
        Phone phone = new Phone();
        phone.setAccountId(testInt);
        phone.setPhoneType(testInt);
        phone.setPhoneNumber(testString);
        phoneDao.create(phone);
    }

    @Test
    void create() {
        Phone phone = new Phone();
        phone.setAccountId(testInt);
        phone.setPhoneType(testInt);
        phone.setPhoneNumber(testString);
        boolean actual = phoneDao.create(phone);
        assertEquals(true, actual);
    }

    @Test
    void get() {
        Phone phone = new Phone();
        phone.setAccountId(testInt);
        phone.setPhoneType(testInt);
        phone.setPhoneNumber(testString);
        phone.setId(testInt);
        List<Phone> list = new ArrayList<>();
        list.add(phone);
        List<Phone> actual = phoneDao.get(testInt);
        assertEquals(list, actual);
    }

    @Test
    void update() {
        Phone phone = new Phone();
        phone.setAccountId(testInt);
        phone.setPhoneType(testInt);
        phone.setPhoneNumber(testString);
        phone.setId(testInt);
        boolean actual = phoneDao.update(phone);
        assertEquals(true, actual);
    }

    @Test
    void delete() {
        Phone phone = new Phone();
        phone.setAccountId(testInt);
        phone.setPhoneType(testInt);
        phone.setPhoneNumber(testString);
        phone.setId(testInt);
        boolean actual = phoneDao.delete(phone);
        assertEquals(true, actual);
    }

}