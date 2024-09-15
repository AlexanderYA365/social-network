package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Group;
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
public class GroupDao {
    private static final Logger logger = LogManager.getLogger(GroupDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    public GroupDao() {
        logger.info("GroupDao(sessionFactory)");
    }

    @Transactional
    public boolean create(Group group) {
        logger.info("create(group = {})", group);
        entityManager.merge(group);
        return true;
    }

    public List<Group> getGroups(int groupId) {
        logger.info("getGroups(groupId = {})", groupId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("groupId"), groupId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Group get(int groupId) {
        logger.info("getGroup(group_id = {})", groupId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("groupId"), groupId));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public Group get(String groupName) {
        logger.info("getGroupByGroupName(groupName = {})", groupName);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("groupName"), groupName));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<Group> getAllGroups(String groupName) {
        logger.info("getGroups(groupName = {})", groupName);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        CriteriaQuery<Group> selectGroupName = criteriaQuery.select(from).where(
                criteriaBuilder.like(from.get("groupName"), groupName));
        return entityManager.createQuery(selectGroupName).getResultList();
    }

    public List<Group> getCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("getAccountsCriteriaLimit(start = {}, end = {}, criteriaName = {})",
                start, end, criteriaName);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> cr = cb.createQuery(Group.class);
        Root<Group> from = cr.from(Group.class);
        cr.select(from);
        CriteriaQuery<Group> nameQuery = cr.select(from).where(cb.like(from.get("groupName"), criteriaName));
        return entityManager.createQuery(nameQuery).setFirstResult(start).setMaxResults(end).getResultList();
    }

    public List<Group> getAllGroups() {
        logger.info("getGroups()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> cr = cb.createQuery(Group.class);
        Root<Group> root = cr.from(Group.class);
        cr.select(root);
        return entityManager.createQuery(cr).getResultList();
    }

    public List<Group> getGroupsAccount(int accountId) {
        logger.info("getGroupsAccount(accountId = {})", accountId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> from = criteriaQuery.from(Group.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("idGroupCreator"), accountId));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Transactional
    public boolean update(Group group) {
        logger.info("update(group = {})", group);
        entityManager.merge(group);
        return true;
    }

    @Transactional
    public boolean delete(Group group) {
        logger.info("delete(group = {})", group);
        group = entityManager.find(Group.class, group.getGroupId());
        entityManager.remove(group);
        return true;
    }

    @Transactional
    public boolean insertIdGroupCreator(Group group, int accountId) {
        logger.info("insertAccount(group = {}, accountId = {})", group, accountId);
        group.setIdGroupCreator(accountId);
        entityManager.merge(group);
        return true;
    }

    public long getSizeRecords() {
        logger.info("getSizeRecords()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Group> from = cq.from(Group.class);
        cq.select(cb.count(from));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public long getSizeRecords(String search) {
        logger.info("getSizeRecords(search = {})", search);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Group> from = cq.from(Group.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.like(from.get("groupName"), search));
        cq.where(conditions.toArray(new Predicate[0]));
        cq.select(cb.count(from));
        return entityManager.createQuery(cq).getSingleResult();
    }

    @Transactional
    public void createGroups(List<Group> groups) {
        logger.info("createGroups(groups.size() = {})", groups.size());
        for (Group a : groups) {
            try {
                entityManager.merge(a);
            } catch (Exception ex) {
                logger.error("exception - " + ex);
            }
        }
    }

}