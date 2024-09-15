package com.getjavajob.training.yakovleva.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhoneTest {

    @Test
    void getIdPhone() {
        Phone phone = new Phone();
        phone.setAccountId(1);
        assertEquals(1, phone.getAccountId());
    }

    @Test
    void getPhoneNumber() {
        Phone phone = new Phone();
        phone.setPhoneNumber("test");
        assertEquals("1", phone.getPhoneNumber());
    }

    @Test
    void getPhoneType() {
        Phone phone = new Phone();
        phone.setPhoneType(1);
        assertEquals(1, phone.getPhoneType());
    }

}