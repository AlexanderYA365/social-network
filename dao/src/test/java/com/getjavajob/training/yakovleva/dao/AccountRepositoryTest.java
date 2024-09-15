package com.getjavajob.training.yakovleva.dao;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.utilsEnum.Role;
import com.getjavajob.training.yakovleva.config.DaoConfig;
import com.getjavajob.training.yakovleva.repository.AccountRepository;
import config.DaoConfigTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DaoConfig.class, DaoConfigTest.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("test")
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RelationsDao relationsDao;
    private String testString = "test";
    private int testInt = 1;

    @Test
    void create() {
        //TODO откорректировать тестовую базу данных
        Account account = new Account();
        account.setId(2);
        account.setRole(Role.ROLE_USER);
        account.setPassword("1111");
        account.setUsername("alex");
//        account.getAccountDetails().setName("alex");
//        account.getAccountDetails().setLastName("alex");
//        account.getAccountDetails().setSurname("alex");
        accountRepository.save(account);
        Account account1 = accountRepository.getByUsernameAndPassword(account.getUsername(),
                account.getPassword());
        assertEquals(account, account1);
    }
}
