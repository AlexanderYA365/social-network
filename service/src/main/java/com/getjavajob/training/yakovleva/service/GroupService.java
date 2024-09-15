package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.dao.GroupDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class GroupService {
    private static final Logger logger = LogManager.getLogger(GroupService.class);
    private GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        logger.info("GroupService(GroupDao groupDao)");
        this.groupDao = groupDao;
    }

    public GroupService() {
    }

    public List<Group> getAccountGroups(Account account) {
        logger.info("getAccountGroups(account = {})", account);
        return groupDao.getGroupsAccount(account.getId());
    }

    public long getSizeRecords() {
        logger.info("getSizeRecords()");
        return groupDao.getSizeRecords();
    }

    public long getSizeRecords(String search) {
        logger.info("getSizeRecords()");
        return groupDao.getSizeRecords(search);
    }

    public boolean createAccountGroups(Group group) {
        logger.info("createAccountGroups(group = {})", group);
        return groupDao.create(group);
    }

    @Transactional
    public boolean update(Group group) {
        logger.info("update(group = {})", group);
        return groupDao.update(group);
    }

    @Transactional
    public boolean delete(Group group) {
        logger.info("delete(group = {})", group);
        return groupDao.delete(group);
    }

    public List<Group> getCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("getCriteriaLimit(start = {}, end = {}, criteriaName = {})", start, end, criteriaName);
        return groupDao.getCriteriaLimit(start, end, criteriaName);
    }

    public List<Group> getAllGroups() {
        logger.info("getAllGroups()");
        return groupDao.getAllGroups();
    }

    public List<Group> getByGroupName(String groupName) {
        logger.info("get(groupName = {})", groupName);
        return groupDao.getAllGroups(groupName);
    }

    public Group get(String groupName) {
        logger.info("get(groupName = {})", groupName);
        return groupDao.get(groupName);
    }

    public List<Group> getGroups(int idGroup) {
        logger.info("get(idGroup = {})", idGroup);
        return groupDao.getGroups(idGroup);
    }

    public Group get(int idGroup) {
        logger.info("get(idGroup = {})", idGroup);
        return groupDao.get(idGroup);
    }

    @Transactional
    public boolean insertAccountGroup(Group group, int accountId) {
        logger.info("insertAccountGroup(group = {}, accountId = {})", group, accountId);
        return groupDao.insertIdGroupCreator(group, accountId);
    }

    @Transactional
    public void createGroups(List<Group> groups) {
        logger.info("createGroups(groups.length = {})", groups.size());
        groupDao.createGroups(groups);
    }

}