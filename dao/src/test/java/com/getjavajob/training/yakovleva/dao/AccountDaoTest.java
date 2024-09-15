package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Relations;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class, DaoConfigTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
public class AccountDaoTest {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private RelationsDao relationsDao;
    private String testString = "test";
    private int testInt = 1;

    @BeforeEach
    void createTestData() {
        Account account = new Account();
        account.setRole(Role.ROLE_USER);
        account.setPassword("2222");
        account.setUsername("alex2");
        Relations relation = new Relations();
        relation.setFriendId(1);
        relation.setAccountId(1);
        accountDao.create(account);
        relationsDao.create(relation);
    }

    @Test
    void create() {
        Account account = new Account();
        account.setId(2);
        account.setRole(Role.ROLE_USER);
        account.setPassword("1111");
        account.setUsername("alex");
        accountDao.create(account);
        Account account1 = accountDao.getAccount(account.getUsername(), account.getPassword());
        assertEquals(account, account1);
    }

    @Test
    void getIdAccount() {
        Account account = new Account();
        account.setPassword("2222");
        account.setUsername("alex2");
        int id = accountDao.getIdAccount(account);
        assertEquals(id, 1);
    }

    @Test
    void getAccountByUsernameAndPassword() {
        Account account = accountDao.getAccount("alex2", "2222");
        assertEquals(account.getId(), 1);
    }

    @Test
    void getAccount() {
        Account account = accountDao.getAccount(1);
        assertEquals(account.getId(), 1);
    }

    @Test
    void getByUsername() {
        Account account = accountDao.getByUsername("alex2");
        assertEquals(account.getId(), 1);
    }

    @Test
    void getAccountsName() {
        Account account = new Account();
        account.setId(1);
        account.setRole(Role.ROLE_USER);
        account.setPassword("2222");
        account.setUsername("alex2");
        List<Account> actual = new ArrayList<>();
        actual.add(account);
        List<Account> accounts = accountDao.getAccountsName("alex2");
        System.out.println(accounts);
        assertEquals(accounts, actual);
    }

    @Test
    void getAllAccounts() {
        Account account = new Account();
        account.setId(1);
        account.setRole(Role.ROLE_USER);
        account.setPassword("2222");
        account.setUsername("alex2");
        List<Account> actual = new ArrayList<>();
        actual.add(account);
        List<Account> accounts = accountDao.getAccountsName("alex2");
        assertEquals(accounts, actual);
    }

    @Test
    void getAccountsCriteriaLimit() {
        Account account = new Account();
        account.setId(1);
        account.setRole(Role.ROLE_USER);
        account.setPassword("2222");
        account.setUsername("alex2");
        List<Account> actual = new ArrayList<>();
        actual.add(account);
        List<Account> accounts = accountDao.getAccountsCriteriaLimit(0, 1, "alex2");
        assertEquals(accounts, actual);
    }

    @Test
    void getSizeRecords() {
        long size = accountDao.getSizeRecords();
        assertEquals(size, 1);
    }

    @Test
    void getSizeRecordsByCriteria() {
        long size = accountDao.getSizeRecords("alex2");
        assertEquals(size, 1);
    }

    @Test
    void getAccountsLimit() {
        Account account = new Account();
        account.setId(1);
        account.setRole(Role.ROLE_USER);
        account.setPassword("2222");
        account.setUsername("alex2");
        List<Account> actual = new ArrayList<>();
        actual.add(account);
        List<Account> accounts = accountDao.getAccountsLimit(0, 1);
        assertEquals(accounts, actual);
    }

    @Test
    void deleteAccount() {
        Account account = new Account();
        account.setId(1);
        account.setRole(Role.ROLE_USER);
        account.setPassword("2222");
        account.setUsername("alex2");
        boolean actual = accountDao.deleteAccount(account);
        assertEquals(true, actual);
    }

    @Test
    void updateAccount() {
        Account account = new Account();
        account.setId(1);
        account.setRole(Role.ROLE_USER);
        account.setPassword("2222");
        account.setUsername("alex2");
        Account expected = accountDao.getAccount(1);
        Account actual = accountDao.updateAccount(account);
        assertEquals(expected, actual);
    }

    @Test
    void getFriendsAccount() {
        List<Account> actual = accountDao.getFriendsAccount(1);
        assertEquals(1, actual.size());
    }

    @Test
    void createAccounts() {
        Account account = new Account();
        account.setId(1);
        account.setRole(Role.ROLE_USER);
        account.setPassword("2222");
        account.setUsername("alex2");
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        accountDao.createAccounts(accounts);
        long actual = accountDao.getSizeRecords();
        assertEquals(1, actual);
    }

}