package com.getjavajob.training.yakovleva.common;

import com.getjavajob.training.yakovleva.common.utilsEnum.GroupRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupMembersTest {
    private final int expectedInt = 1;
    private final GroupRole expectedGroupRole = GroupRole.MEMBER;
    private final Account expectedAccount = new Account();
    private final Group expectedGroup = new Group();

    @BeforeEach
    void creteTestAccount() {
        expectedAccount.setId(expectedInt);
        expectedGroup.setGroupId(expectedInt);
    }

    @Test
    void getId() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setId(expectedInt);
        assertEquals(expectedInt, groupMembers.getId());
    }

    @Test
    void setId() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setId(expectedInt);
        assertEquals(expectedInt, groupMembers.getId());
    }

    @Test
    void getGroup() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(expectedGroup);
        assertEquals(expectedGroup, groupMembers.getGroup());
    }

    @Test
    void setGroup() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroup(expectedGroup);
        assertEquals(expectedGroup, groupMembers.getGroup());
    }

    @Test
    void getMember() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setMember(expectedAccount);
        assertEquals(expectedAccount, groupMembers.getMember());
    }

    @Test
    void setMember() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setMember(expectedAccount);
        assertEquals(expectedAccount, groupMembers.getMember());
    }

    @Test
    void getGroupRole() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroupRole(expectedGroupRole);
        assertEquals(expectedGroupRole, groupMembers.getGroupRole());
    }

    @Test
    void setGroupRole() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroupRole(expectedGroupRole);
        assertEquals(expectedGroupRole, groupMembers.getGroupRole());
    }
}