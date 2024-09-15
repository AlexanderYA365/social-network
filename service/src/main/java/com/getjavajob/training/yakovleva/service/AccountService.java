package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.*;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import com.getjavajob.training.yakovleva.dao.ApplicationDao;
import com.getjavajob.training.yakovleva.dao.PhoneDao;
import com.getjavajob.training.yakovleva.dao.RelationsDao;
import com.getjavajob.training.yakovleva.repository.AccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class);
    private final AccountDao accountDao;
    private final PhoneDao phoneDao;
    private final ApplicationDao applicationDao;
    private final AccountRepository accountRepository;
    private final MessageService messageService;
    private final RelationsDao relationsDao;

    @Autowired
    public AccountService(AccountDao accountDao, PhoneDao phoneDao,
                          ApplicationDao applicationDao,
                          AccountRepository accountRepository,
                          MessageService messageService,
                          RelationsDao relationsDao) {
        logger.info("AccountService(AccountDao accountDao, PhoneDao phoneDao)");
        this.messageService = messageService;
        this.applicationDao = applicationDao;
        this.accountDao = accountDao;
        this.phoneDao = phoneDao;
        this.accountRepository = accountRepository;
        this.relationsDao = relationsDao;
    }

    @Transactional
    public boolean create(Account account, AccountPhoto accountPhoto, List<Phone> phones) {
        logger.info("create(account = {},accountPhoto = {}, phones = {})",
                account, accountPhoto.getPhotoFileName(), phones);
        try {
            account.setAccountPhoto(accountPhoto);
            accountRepository.save(account);
            for (Phone phone : phones) {
                phone.setAccountId(account.getId());
                phoneDao.create(phone);
            }
            return true;
        } catch (Exception e) {
            logger.info("create() exception - {}", e.toString());
            return false;
        }
    }

    @Transactional
    public void createAccounts(List<Account> accounts) {
        logger.info("createAccounts(accounts - {})", accounts);
        try {
            accountRepository.saveAll(accounts);
        } catch (Exception ex) {
            logger.error("createAccounts() exception - {}", ex);
        }
    }

    public int getId(Account account) {
        logger.info("get(account - {})", account);
        return accountRepository.findByUsernameAndPassword(account.getUsername(),
                account.getPassword()).getId();
    }

    @Transactional
    public boolean update(Account account) {
        logger.info("update(account - {})", account);
        try {
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            logger.error("update() exception - {}", e);
        }
        return false;
    }

    @Transactional
    public void updatePhoto(Account account) {
        logger.info("updatePhoto(account = {})", account.getId());
        accountRepository.save(account);
    }

    @Transactional
    public boolean delete(Account account) {
        logger.info("delete(Account accountId = {})", account.getId());
        try {
            for (Phone phone : account.getPhones()) {
                phoneDao.delete(phone);
            }
            accountRepository.delete(account);
            return true;
        } catch (Exception e) {
            logger.error("delete() exception - {}", e);
            return false;
        }
    }

    @Transactional
    public boolean deleteById(int accountId) {
        logger.info("deleteById(accountId = {})", accountId);
        try {
            Account account = accountRepository.getById(accountId);
            boolean result = false;
            for (Phone phone : account.getPhones()) {
                phone.setAccountId(accountId);
                result = phoneDao.delete(phone);
            }
            return accountDao.deleteAccount(account) || result;
        } catch (Exception e) {
            logger.error("deleteById() exception - {}", e);
            return false;
        }
    }

    public Account get(int accountId) {
        logger.info("get(accountId = {})", accountId);
        return accountRepository.getById(accountId);
    }

    public List<Account> getSenderMessageGroup(Group group, Pageable pageable) {
        logger.info("getSenderMessageAccounts(group = {}, pageable = {})", group, pageable);
        List<Message> messages = messageService.getWallMassagesGroup(group, pageable);
        List<Account> senderAccounts = new ArrayList<>();
        for (Message message : messages) {
            senderAccounts.add(accountRepository.getById(message.getSenderId()));
        }
        return senderAccounts;
    }

    public List<Account> getSenderMessageAccounts(Account account, Pageable pageable) {
        logger.info("getSenderMessageAccounts(account = {}, pageable = {})", account, pageable);
        List<Message> messages = messageService.getWallMassageAccount(account, pageable);
        List<Account> senderAccounts = new ArrayList<>();
        for (Message message : messages) {
            senderAccounts.add(accountRepository.getById(message.getSenderId()));
        }
        return senderAccounts;
    }

    public Account get(String username) {
        logger.info("get(username = {})", username);
        return accountRepository.findByUsername(username);
    }

    public List<Account> getFriendRequests(Account account) {
        logger.info("getFriendRequests(account = {})", account);
        List<Application> applications = applicationDao.get(account);
        return getAccounts(applications);
    }

    private List<Account> getAccounts(List<Application> applications) {
        List<Account> accounts = new ArrayList<>();
        for (Application application : applications) {
            if (application.getApplicantId() != 0) {
                accounts.add(accountRepository.findById(application.getApplicantId()).get());
            }
        }
        return accounts;
    }

    public List<Account> getFriendRejected(Account account, int start, int quantity) {
        logger.info("getFriendRejected(account = {}, start = {}, quantity = {})", account, start, quantity);
        List<Application> applications = applicationDao.getRejectedUsers(account, start, quantity);
        return getAccounts(applications);
    }

    public List<Account> getAccountsCriteriaLimit(int start, int end, String criteriaName) {
        logger.info("getAccountsCriteriaLimit(start = {}, end = {}, criteriaName = {})", start, end, criteriaName);
        Pageable pageable = PageRequest.of(start, end - start);
        return accountRepository.getAccountsCriteriaLimit(criteriaName, pageable);
    }

    public List<Account> getAllAccountsLimit(int start, int end) {
        logger.info("getAllAccountsLimit(start = {}, end = {})", start, end);
        Pageable pageable = PageRequest.of(start, end - start);
        return accountRepository.getAccountsLimit(pageable);
    }

    public long getSizeRecords() {
        logger.info("getSizeRecords()");
        return accountRepository.getSize();
    }

    public long getSizeRecords(String search) {
        logger.info("getSizeRecords()");
        return accountRepository.getSizeRecords(search);
    }

    public Account getByUsername(String username) {
        logger.info("getByUsername(username = {})", username);
        return accountRepository.findByUsername(username);
    }

    public List<Account> getFriendsAccount(int accountId, int start, int end) {
        logger.info("getFriendsAccount(accountId = {}, start = {}, end = {})", accountId, start, end);
        List<Relations> relations = relationsDao.getFriendsAccount(accountId, start, end);
        List<Account> accounts = new ArrayList<>();
        for (Relations relation : relations) {
            accounts.add(accountRepository.getById(relation.getFriendId()));
        }
        return accounts;
    }

    public List<Account> getAll(){
        logger.info("getAll()");
        return accountRepository.getAll();
    }

}