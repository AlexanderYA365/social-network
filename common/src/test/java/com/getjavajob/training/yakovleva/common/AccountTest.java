package com.getjavajob.training.yakovleva.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {
    private final String expectedString = "test";

    @Test
    public void getUsername() {
        Account account = new Account();
        account.setUsername("test");
        assertEquals(expectedString, account.getUsername());
    }

    @Test
    public void getPassword() {
        Account account = new Account();
        account.setPassword("test");
        assertEquals(expectedString, account.getPassword());
    }

    @Test
    public void getId() {
        Account account = new Account();
        account.setId(1);
        assertEquals(1, account.getId());
    }

    @Test
    public void setUsername() {
        Account account = new Account();
        account.setUsername("test");
        assertEquals(expectedString, account.getUsername());
    }

    @Test
    public void setPassword() {
        Account account = new Account();
        account.setPassword("test");
        assertEquals(expectedString, account.getPassword());
    }

    @Test
    public void setRole() {
        Account account = new Account();
        account.setRole(1);
        assertEquals(1, account.getRole());
    }

    @Test
    public void setId() {
        Account account = new Account();
        account.setId(1);
        assertEquals(1, account.getId());
    }

    @Test
    public void testToString() {
        Account account = new Account();
        account.setUsername("test");
        assertEquals(expectedString, account.getUsername());
    }

}