package com.getjavajob.training.yakovleva.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupTest {
    private byte[] expectedBytes = {0};

    @Test
    void getIdAccount() {
        Group group = new Group();
        group.setIdGroupCreator(1);
        assertEquals(1, group.getIdGroupCreator());
    }

    @Test
    void getGroupName() {
        Group group = new Group();
        group.setGroupName("test");
        assertEquals("test", group.getGroupName());
    }

    @Test
    void getLogo() {
        Group group = new Group();
        group.setLogo(expectedBytes);
        assertEquals(expectedBytes, group.getLogo());
    }

    @Test
    void getIdAdministrator() {
        Group group = new Group();
        group.setIdGroupCreator(1);
        assertEquals(1, group.getIdGroupCreator());
    }

    @Test
    void getIdGroup() {
        Group group = new Group();
        group.setGroupId(1);
        assertEquals(1, group.getGroupId());
    }

    @Test
    void setIdGroup() {
        Group group = new Group();
        group.setGroupId(1);
        assertEquals(1, group.getGroupId());
    }

    @Test
    void setIdAccount() {
        Group group = new Group();
        group.setIdGroupCreator(1);
        assertEquals(1, group.getIdGroupCreator());
    }

    @Test
    void setGroupName() {
        Group group = new Group();
        group.setGroupName("test");
        assertEquals("test", group.getGroupName());
    }

    @Test
    void setLogo() {
        Group group = new Group();
        group.setLogo(expectedBytes);
        assertEquals(expectedBytes, group.getLogo());
    }

    @Test
    void setIdAdministrator() {
        Group group = new Group();
        group.setIdGroupCreator(1);
        assertEquals(1, group.getIdGroupCreator());
    }

}