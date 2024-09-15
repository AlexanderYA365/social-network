package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.common.utilsEnum.ApplicationStatusType;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class, DaoConfigTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
class ApplicationDaoTest {
    @Autowired
    private ApplicationDao applicationDao;
    private String testString = "test";

    @BeforeEach
    void createTestData() {
        for (int i = 0; i < 3; i++) {
            Application application = new Application();
            application.setApplicantId(1);
            application.setRecipientId(1);
            application.setStatus(i);
            application.setApplicationType(i > 1 == true ? 0 : 1);
            applicationDao.create(application);
        }
    }

    @Test
    void create() {
        Application application = new Application();
        application.setApplicantId(1);
        application.setRecipientId(1);
        application.setId(1);
        application.setStatus(0);
        application.setApplicationType(0);
        applicationDao.create(application);
        Application actual = applicationDao.get(1);
        assertEquals(application, actual);
    }

    @Test
    void getById() {
        Application application1 = applicationDao.get(1);
        assertEquals(1, application1.getId());
    }

    @Test
    void getByGroupAndRecipientId() {
        Group group = new Group();
        group.setGroupId(1);
        group.setIdGroupCreator(1);
//        group.setLogo(testString);
        group.setGroupName(testString);
        group.setInfo(testString);
        Application application1 = applicationDao.get(group, 1);
        assertEquals(3, application1.getId());

    }

    @Test
    void getByRelations() {
        Relations relations = new Relations();
        relations.setAccountId(1);
        relations.setFriendId(1);
        Application application1 = applicationDao.get(relations);
        assertEquals(1, application1.getId());
    }

    @Test
    void getApplications() {
        List<Application> actual = applicationDao.getApplications();
        assertEquals(3, actual.size());
    }

    @Test
    void update() {
        Application expected = applicationDao.get(1);
        expected.setStatus(ApplicationStatusType.REJECTED);
        boolean actual = applicationDao.update(expected);
        Application actualApplicant = applicationDao.get(1);
        assertEquals(expected, actualApplicant);
        assertEquals(true, actual);
    }

    @Test
    void delete() {
        Application application = new Application();
        application.setApplicantId(1);
        application.setRecipientId(1);
        application.setId(1);
        application.setStatus(0);
        application.setApplicationType(0);
        applicationDao.create(application);
        boolean actual = applicationDao.delete(application);
        assertEquals(true, actual);
    }

}