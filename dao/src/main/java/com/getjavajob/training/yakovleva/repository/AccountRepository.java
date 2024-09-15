package com.getjavajob.training.yakovleva.repository;

import com.getjavajob.training.yakovleva.common.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountRepository extends PagingAndSortingRepository<Account, Integer> {

    Account getById(int id);

    Account getByUsernameAndPassword(String username, String password);

    Account findByUsernameAndPassword(String username, String password);

    Account findByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.accountDetails.name=:searchParameter or " +
            "a.accountDetails.lastName=:searchParameter or a.accountDetails.surname=:searchParameter")
    List<Account> getAccountsCriteriaLimit(String searchParameter, Pageable pageable);

    @Query("SELECT a FROM Account a")
    List<Account> getAccountsLimit(Pageable pageable);

    @Transactional
    Account save(Account account);

    @Query("SELECT COUNT (a) FROM Account a")
    long getSize();

    @Query("SELECT a FROM Account a")
    List<Account> getAll();
    @Query("SELECT COUNT (a) FROM Account a WHERE a.accountDetails.name=:searchParameter or " +
            "a.accountDetails.lastName=:searchParameter or a.accountDetails.surname=:searchParameter")
    long getSizeRecords(String searchParameter);

    @Query("SELECT a FROM Account a WHERE a.accountDetails.name=:name")
    List<Account> getAccountsByName(String name);

}