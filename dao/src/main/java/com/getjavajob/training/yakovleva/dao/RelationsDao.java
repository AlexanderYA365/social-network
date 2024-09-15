package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Relations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelationsDao {
    private static final Logger logger = LogManager.getLogger(RelationsDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    public RelationsDao() {
        logger.info("RelationsDao(sessionFactory)");
    }

    @Transactional
    public boolean create(Relations relations) {
        logger.info("create(relations = {})", relations);
        entityManager.merge(relations);
        return true;
    }

    public List<Relations> getAll() {
        logger.info("getAll()");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        CriteriaQuery<Relations> nameQuery = criteriaQuery.select(from);
        return entityManager.createQuery(nameQuery).getResultList();
    }

    public List<Relations> getByAccountId(Relations relations) {
        logger.info("getByAccountId(relations = {})", relations);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        CriteriaQuery<Relations> nameQuery = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("accountId"), relations.getAccountId()));
        return entityManager.createQuery(nameQuery).getResultList();
    }

    public Relations getByFriendId(Relations relations) {
        logger.info("getByFriendId(relations = {})", relations);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(from.get("accountId"), relations.getAccountId()));
        conditions.add(criteriaBuilder.equal(from.get("friendId"), relations.getFriendId()));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<Relations> getByAccountId(int accountId) {
        logger.info("getByAccountId(accountId = {})", accountId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> criteriaQuery = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> from = criteriaQuery.from(Relations.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.equal(from.get("accountId"), accountId));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Transactional
    public boolean update(Relations relations) {
        logger.info("update(relations = {})", relations);
        entityManager.merge(relations);
        return true;
    }

    @Transactional
    public boolean deleteByAccountId(Relations relations) {
        logger.info("deleteByAccountId(relations = {})", relations);
        entityManager.remove(relations);
        return true;
    }

    @Transactional
    public void createRelations(List<Relations> relations) {
        logger.info("createRelations(relations.size() = {})", relations.size());
        for (Relations a : relations) {
            try {
                entityManager.merge(a);
            } catch (Exception ex) {
                logger.error("exception - " + ex);
            }
        }
    }

    public long getSizeRecordsFriends(int accountId) {
        logger.info("getSizeRecordsFriends(accountId = {})", accountId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Relations> from = cq.from(Relations.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.or(
                cb.equal(from.get("accountId"), accountId),
                cb.equal(from.get("friendId"), accountId)));
        cq.where(conditions.toArray(new Predicate[0]));
        cq.select(cb.count(from));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public List<Relations> getFriendsAccount(int accountId, int start, int end) {
        logger.info("getFriendsAccount(accountId = {}, start = {}, end = {})", accountId, start, end);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> query = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> root = query.from(Relations.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get("accountId"), accountId));
        return entityManager.createQuery(query).setFirstResult(start)
                .setMaxResults(end).getResultList();
    }

}