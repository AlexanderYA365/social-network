package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
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
public class AccountDao {
    private static final Logger logger = LogManager.getLogger(AccountDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    public AccountDao() {
        logger.info("create accountDao");
    }

    @Transactional
    public boolean create(Account account) {
        logger.info("create(account = {})", account);
        entityManager.merge(account);
        return true;
    }

    public int getIdAccount(Account account) {
        logger.info("getIdAccount(account = {})", account);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.like(from.get("username"), account.getUsername()));
        conditions.add(criteriaBuilder.like(from.get("password"), account.getPassword()));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getSingleResult().getId();
    }

    public Account getAccount(String username, String password) {
        logger.info("getAccount(username = {}, password = {})", username, password);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(criteriaBuilder.like(from.get("username"), username));
        conditions.add(criteriaBuilder.like(from.get("password"), password));
        criteriaQuery.where(conditions.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public Account getAccount(int id) {
        logger.info("getAccount(id = {})", id);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("id"), id));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public Account getAccount(String username) {
        logger.info("getAccount(username = {})", username);
        long time = System.currentTimeMillis();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("username"), username));
        Account account = entityManager.createQuery(criteriaQuery).getSingleResult();
        long timeEnd = System.currentTimeMillis() - time;
        logger.info("timeEnd = {}", timeEnd);
        return account;
    }

    public Account getByUsername(String username) {
        logger.info("getAccount(username = {})", username);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        criteriaQuery.select(from);
        criteriaQuery.where(criteriaBuilder.equal(from.get("username"), username));
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public List<Account> getAccountsName(String name) {
        logger.info("getAccountsName(name = {})", name);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = criteriaQuery.from(Account.class);
        CriteriaQuery<Account> nameQuery = criteriaQuery.select(from).where(
                criteriaBuilder.like(from.get("name"), name));
        return entityManager.createQuery(nameQuery).getResultList();
    }

    public List<Account> getAllAccounts() {
        logger.info("getAccounts()");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> root = cr.from(Account.class);
        cr.select(root);
        return entityManager.createQuery(cr).getResultList();
    }

    public List<Account> getAccountsCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("getAccountsCriteriaLimit(start = {}, end = {}, criteriaName = {})",
                start, end, criteriaName);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> from = cr.from(Account.class);
        cr.select(from);
        CriteriaQuery<Account> nameQuery = cr.select(from).where(
                cb.or(cb.like(from.get("name"), criteriaName),
                        cb.like(from.get("surname"), criteriaName),
                        cb.like(from.get("lastName"), criteriaName)));
        return entityManager.createQuery(nameQuery).setFirstResult(start).setMaxResults(end).getResultList();
    }

    public long getSizeRecords() {
        logger.info("getSizeRecords");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Account> from = cq.from(Account.class);
        cq.select(cb.count(from));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public long getSizeRecords(String search) {
        logger.info("getSizeRecords(search = {})", search);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Account> from = cq.from(Account.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.or(
                cb.like(from.get("name"), search),
                cb.like(from.get("surname"), search),
                cb.like(from.get("lastName"), search)));
        cq.where(conditions.toArray(new Predicate[0]));
        cq.select(cb.count(from));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public List<Account> getAccountsLimit(int start, int end) {
        logger.info("getAccountsLimit(start = {}, end = {})", start, end);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Account> cr = cb.createQuery(Account.class);
        Root<Account> root = cr.from(Account.class);
        cr.select(root);
        return entityManager.createQuery(cr).setFirstResult(start).setMaxResults(end).getResultList();
    }

    @Transactional
    public boolean deleteAccount(Account account) {
        logger.info("getFriendsAccount(account.id = {})", account.getId());
        account = entityManager.find(Account.class, account.getId());
        entityManager.remove(account);
        return true;
    }

    @Transactional
    public Account updateAccount(Account account) {
        logger.info("updateAccount(account = {})", account);
        return entityManager.merge(account);
    }

    public List<Account> getFriendsAccount(int accountId) {
        logger.info("getFriendsAccount(accountId = {})", accountId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Relations> query = criteriaBuilder.createQuery(Relations.class);
        Root<Relations> root = query.from(Relations.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get("accountId"), accountId));
        List<Relations> relations = entityManager.createQuery(query).getResultList();
        List<Account> friends = new ArrayList<>();
        for (Relations accountFriends : relations) {
            friends.add(getAccount(accountFriends.getFriendId()));
        }
        return friends;
    }

    @Transactional
    public void createAccounts(List<Account> accounts) {
        logger.info("createAccounts(accounts.size() = {})", accounts.size());
        for (Account a : accounts) {
            try {
                entityManager.merge(a);
            } catch (Exception ex) {
                logger.error("exception - " + ex);
            }
        }
    }

}