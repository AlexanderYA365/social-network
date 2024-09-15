package com.getjavajob.training.yakovleva.service.junit;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.RelationsService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountServiceTest {

    @Test
    void createTrue() {
        Account account = Mockito.mock(Account.class);
        AccountService accountService = Mockito.mock(AccountService.class);
//        Mockito.when(accountService.create(account)).thenReturn(true);
//        Assert.assertEquals(true, accountService.create(account));
    }

    @Test
    void createFalse() {
        Account account = Mockito.mock(Account.class);
        AccountService accountService = Mockito.mock(AccountService.class);
//        Mockito.when(accountService.create(account)).thenReturn(false);
//        Assert.assertEquals(false, accountService.create(account));
    }

    @Test
    void updateTrue() {
        Account account = Mockito.mock(Account.class);
        AccountService accountService = Mockito.mock(AccountService.class);
//        Mockito.when(accountService.update(account)).thenReturn(true);
//        Assert.assertEquals(true, accountService.update(account));
    }

    @Test
    void updateFalse() {
        Account account = Mockito.mock(Account.class);
        AccountService accountService = Mockito.mock(AccountService.class);
//        Mockito.when(accountService.update(account)).thenReturn(false);
//        Assert.assertEquals(false, accountService.update(account));
    }

    @Test
    void deleteTrue() {
        Account account = Mockito.mock(Account.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.delete(account)).thenReturn(true);
        Assert.assertEquals(true, accountService.delete(account));
    }

    @Test
    void deleteFalse() {
        Account account = Mockito.mock(Account.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        Mockito.when(accountService.delete(account)).thenReturn(false);
        Assert.assertEquals(false, accountService.delete(account));
    }

    @Test
    void addFriendTrue() {
        Account account = Mockito.mock(Account.class);
        RelationsService accountService = Mockito.mock(RelationsService.class);
        //Mockito.when(accountService.create(account, account)).thenReturn(true);
//        Assert.assertEquals(true, accountService.addFriend(account, account));
    }

    @Test
    void addFriendFalse() {
        Account account = Mockito.mock(Account.class);
//        FriendService accountService = Mockito.mock(FriendService.class);
//        Mockito.when(accountService.addFriend(account, account)).thenReturn(false);
//        Assert.assertEquals(false, accountService.addFriend(account, account));
    }

    @Test
    void deleteFriendTrue() {
        Account account = Mockito.mock(Account.class);
//        FriendService accountService = Mockito.mock(FriendService.class);
//        Mockito.when(accountService.deleteFriend(account, account)).thenReturn(true);
//        Assert.assertEquals(true, accountService.deleteFriend(account, account));
    }

    @Test
    void deleteFriendFalse() {
        Account account = Mockito.mock(Account.class);
//        FriendService accountService = Mockito.mock(FriendService.class);
//        Mockito.when(accountService.deleteFriend(account, account)).thenReturn(false);
//        Assert.assertEquals(false, accountService.deleteFriend(account, account));
    }

    @Test
    void accountFriends() {
        Account account = Mockito.mock(Account.class);
//        FriendService accountService = Mockito.mock(FriendService.class);
//        Mockito.when(accountService.accountFriends(account)).thenReturn(null);
//        Assert.assertEquals(null, accountService.accountFriends(account));
    }

}