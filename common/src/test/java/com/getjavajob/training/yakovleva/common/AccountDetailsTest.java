package com.getjavajob.training.yakovleva.common;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountDetailsTest {
    private final String expectedString = "test";

    @Test
    void getId() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setId(1);
        assertEquals(1, accountDetails.getId());
    }

    @Test
    void setId() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setId(1);
        assertEquals(1, accountDetails.getId());
    }

    @Test
    public void getName() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setName("test");
        assertEquals(expectedString, accountDetails.getName());
    }


    @Test
    void setName() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setName("test");
        assertEquals(expectedString, accountDetails.getName());
    }

    @Test
    void getSurname() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setSurname("test");
        assertEquals(expectedString, accountDetails.getSurname());
    }

    @Test
    void setSurname() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setSurname("test");
        assertEquals(expectedString, accountDetails.getSurname());
    }

    @Test
    void getLastName() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setLastName("test");
        assertEquals(expectedString, accountDetails.getLastName());
    }

    @Test
    void setLastName() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setLastName("test");
        assertEquals(expectedString, accountDetails.getLastName());
    }

    @Test
    void getDate() {
        Date expectedDate = new Date();
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setDate(expectedDate);
        assertEquals(expectedDate, accountDetails.getDate());
    }

    @Test
    void setDate() {
        Date expectedDate = new Date();
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setDate(expectedDate);
        assertEquals(expectedDate, accountDetails.getDate());
    }

    @Test
    void getIcq() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setIcq(1);
        assertEquals(1, accountDetails.getIcq());
    }

    @Test
    void setIcq() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setIcq(1);
        assertEquals(1, accountDetails.getIcq());
    }

    @Test
    void getAddressHome() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAddressHome("test");
        assertEquals(expectedString, accountDetails.getAddressHome());
    }

    @Test
    void setAddressHome() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAddressHome("test");
        assertEquals(expectedString, accountDetails.getAddressHome());
    }

    @Test
    void getAddressJob() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAddressJob("test");
        assertEquals(expectedString, accountDetails.getAddressJob());
    }

    @Test
    void setAddressJob() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAddressJob("test");
        assertEquals(expectedString, accountDetails.getAddressJob());
    }

    @Test
    void getEmail() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setEmail("test");
        assertEquals(expectedString, accountDetails.getEmail());
    }

    @Test
    void setEmail() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setEmail("test");
        assertEquals(expectedString, accountDetails.getEmail());
    }

    @Test
    void getAboutMe() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAboutMe("test");
        assertEquals(expectedString, accountDetails.getAboutMe());
    }

    @Test
    void setAboutMe() {
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setAboutMe("test");
        assertEquals(expectedString, accountDetails.getAboutMe());
    }
    
}