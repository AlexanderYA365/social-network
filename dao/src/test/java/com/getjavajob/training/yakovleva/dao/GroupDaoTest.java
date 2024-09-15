package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
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
class GroupDaoTest {
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private AccountDao accountDao;
    private String testString = "test";
    private int testInt = 1;

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
        group.setIdGroupCreator(1);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        groupDao.create(group);
    }

    @Test
    void create() {
        Group group = new Group();
        group.setIdGroupCreator(1);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        boolean actual = groupDao.create(group);
        assertEquals(true, actual);
    }

    @Test
    void getGroups() {
        Group group = new Group();
        group.setIdGroupCreator(testInt);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        group.setGroupId(testInt);
        List<Group> expected = new ArrayList<>();
        expected.add(group);
        List<Group> actual = groupDao.getGroups(testInt);
        assertEquals(expected, actual);
    }

    @Test
    void getId() {
        Group expected = new Group();
        expected.setIdGroupCreator(testInt);
//        expected.setLogo(testString);
        expected.setGroupName(testString);
        expected.setInfo(testString);
        expected.setGroupId(testInt);
        Group actual = groupDao.get(testInt);
        assertEquals(expected, actual);
    }

    @Test
    void testGroupName() {
        Group expected = new Group();
        expected.setIdGroupCreator(testInt);
//        expected.setLogo(testString);
        expected.setGroupName(testString);
        expected.setInfo(testString);
        expected.setGroupId(testInt);
        Group actual = groupDao.get(testString);
        assertEquals(expected, actual);
    }

    @Test
    void getAllGroupsByGroupName() {
        Group group = new Group();
        group.setIdGroupCreator(testInt);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        group.setGroupId(testInt);
        List<Group> expected = new ArrayList<>();
        expected.add(group);
        List<Group> actual = groupDao.getAllGroups(testString);
        assertEquals(expected, actual);
    }

    @Test
    void getCriteriaLimit() {
        Group group = new Group();
        group.setIdGroupCreator(testInt);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        group.setGroupId(testInt);
        List<Group> expected = new ArrayList<>();
        expected.add(group);
        List<Group> actual = groupDao.getCriteriaLimit(0, 1, testString);
        assertEquals(expected, actual);
    }

    @Test
    void getAllGroups() {
        Group group = new Group();
        group.setIdGroupCreator(testInt);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        group.setGroupId(testInt);
        List<Group> expected = new ArrayList<>();
        expected.add(group);
        List<Group> actual = groupDao.getAllGroups();
        assertEquals(expected, actual);
    }

    @Test
    void getGroupsAccount() {
        Group group = new Group();
        group.setIdGroupCreator(testInt);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        group.setGroupId(testInt);
        List<Group> expected = new ArrayList<>();
        expected.add(group);
        List<Group> actual = groupDao.getGroupsAccount(testInt);
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        Group group = new Group();
        group.setIdGroupCreator(testInt);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        group.setGroupId(testInt);
        boolean actual = groupDao.update(group);
        assertEquals(true, actual);
    }

    @Test
    void delete() {
        Group group = new Group();
        group.setIdGroupCreator(testInt);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        group.setGroupId(testInt);
        boolean actual = groupDao.delete(group);
        assertEquals(true, actual);
    }

    @Test
    void insertIdGroupCreator() {
        Group group = new Group();
        group.setIdGroupCreator(testInt);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        group.setGroupId(testInt);
        boolean actual = groupDao.insertIdGroupCreator(group, 2);
        assertEquals(true, actual);
    }

    @Test
    void getSizeRecords() {
        long actual = groupDao.getSizeRecords();
        assertEquals(1, actual);
    }

    @Test
    void getSizeRecordsBySearch() {
        long actual = groupDao.getSizeRecords(testString);
        assertEquals(1, actual);
    }

}