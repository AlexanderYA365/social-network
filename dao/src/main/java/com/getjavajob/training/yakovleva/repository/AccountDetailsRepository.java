package com.getjavajob.training.yakovleva.repository;

import com.getjavajob.training.yakovleva.common.AccountDetails;
import org.springframework.data.repository.CrudRepository;

public interface AccountDetailsRepository extends CrudRepository<AccountDetails, Integer> {

    AccountDetails save(AccountDetails accountDetails);

}
