package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Phone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class PhoneDao {
    private static final Logger logger = LogManager.getLogger(PhoneDao.class);
    @PersistenceContext
    private EntityManager entityManager;

    public PhoneDao() {
        logger.info("PhoneDao()");
    }

    @Transactional
    public boolean create(final Phone phone) {
        logger.info("create(phone = {})", phone);
        entityManager.merge(phone);
        return true;
    }

    public List<Phone> get(int accountId) {
        logger.info("get(accountId = {})", accountId);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Phone> criteriaQuery = criteriaBuilder.createQuery(Phone.class);
        Root<Phone> from = criteriaQuery.from(Phone.class);
        CriteriaQuery<Phone> selectAccountId = criteriaQuery.select(from).where(
                criteriaBuilder.equal(from.get("accountId"), accountId));
        return entityManager.createQuery(selectAccountId).getResultList();
    }

    @Transactional
    public boolean update(Phone phone) {
        logger.info("update(phone = {})", phone);
        entityManager.merge(phone);
        return true;
    }

    @Transactional
    public boolean delete(Phone phone) {
        logger.info("delete(phone = {})", phone);
        phone = entityManager.find(Phone.class, phone.getId());
        entityManager.remove(phone);
        return true;
    }

}