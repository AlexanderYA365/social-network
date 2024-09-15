package com.getjavajob.training.yakovleva.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTest {
    private final int expectedInt = 1;

    @Test
    void getRecipientId() {
        Application application = new Application();
        application.setRecipientId(expectedInt);
        assertEquals(expectedInt, application.getRecipientId());
    }

    @Test
    void setRecipientId() {
        Application application = new Application();
        application.setRecipientId(expectedInt);
        assertEquals(expectedInt, application.getRecipientId());
    }

    @Test
    void getId() {
        Application application = new Application();
        application.setId(expectedInt);
        assertEquals(expectedInt, application.getId());
    }

    @Test
    void setId() {
        Application application = new Application();
        application.setId(expectedInt);
        assertEquals(expectedInt, application.getId());
    }

    @Test
    void getApplicationType() {
        Application application = new Application();
        application.setApplicationType(expectedInt);
        assertEquals(expectedInt, application.getApplicationType());
    }

    @Test
    void setApplicationType() {
        Application application = new Application();
        application.setApplicationType(expectedInt);
        assertEquals(expectedInt, application.getApplicationType());
    }

    @Test
    void getApplicantId() {
        Application application = new Application();
        application.setApplicantId(expectedInt);
        assertEquals(expectedInt, application.getApplicantId());
    }

    @Test
    void setApplicantId() {
        Application application = new Application();
        application.setApplicationType(expectedInt);
        assertEquals(expectedInt, application.getApplicationType());
    }

    @Test
    void getStatus() {
        Application application = new Application();
        application.setStatus(expectedInt);
        assertEquals(expectedInt, application.getStatus());
    }

    @Test
    void setStatus() {
        Application application = new Application();
        application.setStatus(expectedInt);
        assertEquals(expectedInt, application.getStatus());
    }

}