package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.dao.RelationsDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RelationsService {
    private static final Logger logger = LogManager.getLogger(RelationsService.class);
    private final RelationsDao relationsDao;

    @Autowired
    public RelationsService(RelationsDao relationsDao) {
        logger.info("RelationsService(RelationsDao relationsDao)");
        this.relationsDao = relationsDao;
    }

    @Transactional
    public boolean create(Relations relations) {
        logger.info("create(relations = {})", relations);
        return relationsDao.create(relations);
    }

    public List<Relations> getByAccountId(Relations relations) {
        logger.info("getByAccountId(relations = {})", relations);
        return relationsDao.getByAccountId(relations);
    }

    public List<Relations> getAll() {
        logger.info("getAll()");
        return relationsDao.getAll();
    }

    public long getSizeRecordsFriends(int accountId) {
        logger.info("getSizeRecordsFriends(accountId = {})", accountId);
        return relationsDao.getSizeRecordsFriends(accountId);
    }

    public Relations getByFriendId(Relations relations) {
        logger.info("getByFriendId(relations = {})", relations);
        return relationsDao.getByFriendId(relations);
    }

    @Transactional
    public boolean update(Relations relations) {
        logger.info("update(relations = {})", relations);
        return relationsDao.update(relations);
    }

    @Transactional
    public boolean deleteByAccountId(Relations relations) {
        logger.info("deleteByAccountId(relations = {})", relations);
        return relationsDao.deleteByAccountId(relations);
    }

    @Transactional
    public void createRelations(List<Relations> relations) {
        logger.info("createRelations(relations = {})", relations);
        relationsDao.createRelations(relations);
    }

}