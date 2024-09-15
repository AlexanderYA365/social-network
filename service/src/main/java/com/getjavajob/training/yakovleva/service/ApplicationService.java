package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.common.utilsEnum.ApplicationStatusType;
import com.getjavajob.training.yakovleva.common.utilsEnum.ApplicationType;
import com.getjavajob.training.yakovleva.dao.ApplicationDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ApplicationService {
    private static final Logger logger = LogManager.getLogger(ApplicationService.class);
    private ApplicationDao applicationDao;

    @Autowired
    public ApplicationService(ApplicationDao applicationDao) {
        logger.info("ApplicationService(ApplicationDao applicationDao)");
        this.applicationDao = applicationDao;
    }

    public ApplicationService() {
    }

    @Transactional
    public boolean create(Application application) {
        logger.info("create(application = {})", application);
        return applicationDao.create(application);
    }

    @Transactional
    public boolean update(Application application) {
        logger.info("update(application = {})", application);
        try {
            applicationDao.update(application);
            return true;
        } catch (Exception ex) {
            logger.error("update Exception = {}", ex);
        }
        return false;
    }

    @Transactional
    public boolean delete(Application application) {
        logger.info("delete(application = {})", application);
        return applicationDao.delete(application);
    }

    public Application get(int idApplication) {
        logger.info("get(idApplication - {})", idApplication);
        return applicationDao.get(idApplication);
    }

    public long getSizeRecords(int recipientId, ApplicationStatusType status, ApplicationType applicationType) {
        logger.info("getSizeRecords(recipientId = {}, status = {}, applicationType = {})", recipientId, status, applicationType);
        return applicationDao.getSizeRecords(recipientId, status, applicationType);
    }

    public Application get(int applicantId, int recipientId) {
        logger.info("get(applicantId = {}, recipientId = {})", applicantId, recipientId);
        return applicationDao.get(applicantId, recipientId);
    }

    public Application getGroupAccount(Group group, int recipientId) {
        logger.info("getGroupAccount(group = {}, recipientId = {})", group, recipientId);
        return applicationDao.get(group, recipientId);
    }

    public Application getAccount(Relations relations) {
        logger.info("getAccount(relations = {})", relations);
        return applicationDao.get(relations);
    }

    public List<Application> getAllApplication() {
        logger.info("getAllApplication()");
        return applicationDao.getApplications();
    }

    @Transactional
    public void create(List<Application> applications) {
        logger.info("createAll(applications.length = {})", applications.size());
        applicationDao.create(applications);
    }

}