package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.utilsEnum.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetailsService implements UserDetailsService {
    private static final Logger logger = LogManager.getLogger(DetailsService.class);
    private AccountService accountService;

    @Autowired
    public DetailsService(AccountService accountService) {
        this.accountService = accountService;
        logger.info("UserDetailService");
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("loadUserByUsername(username - {})", username);
        Account account;
        try {
            account = accountService.getByUsername(username);
        } catch (Exception e) {
            logger.info("Username = {} not found.", username);
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }
        logger.info("account.getPassword() - {}", account.getPassword());
        return new User(account.getUsername(), account.getPassword(), true, true, true,
                true, getGrantedAuthorities(account));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.values()[account.getRole()].toString()));
        logger.info("authorities - {}", authorities);
        return authorities;
    }

}