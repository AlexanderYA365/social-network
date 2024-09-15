package com.getjavajob.training.yakovleva.service.junit;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.service.GroupService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class GroupServiceTest {

    @Test
    void getAccountGroups() {
        Account account = Mockito.mock(Account.class);
        List<Group> groups = Mockito.mock(List.class);
        GroupService groupService = Mockito.mock(GroupService.class);
        Mockito.when(groupService.getAccountGroups(account)).thenReturn(groups);
        Assert.assertEquals(groups, groupService.getAccountGroups(account));
    }

    @Test
    void createTrue() {
        Group group = Mockito.mock(Group.class);
        GroupService groupService = Mockito.mock(GroupService.class);
        Mockito.when(groupService.createAccountGroups(group)).thenReturn(true);
        Assert.assertEquals(true, groupService.createAccountGroups(group));
    }

    @Test
    void createFalse() {
        Group group = Mockito.mock(Group.class);
        GroupService groupService = Mockito.mock(GroupService.class);
        Mockito.when(groupService.createAccountGroups(group)).thenReturn(false);
        Assert.assertEquals(false, groupService.createAccountGroups(group));
    }

    @Test
    void getGroups() {
        List<Group> groups = Mockito.mock(List.class);
        GroupService groupService = Mockito.mock(GroupService.class);
        Mockito.when(groupService.getAllGroups()).thenReturn(groups);
        Assert.assertEquals(groups, groupService.getAllGroups());
    }

    @Test
    void getGroupName() {
        List<Group> groups = Mockito.mock(List.class);
        GroupService groupService = Mockito.mock(GroupService.class);
        Mockito.when(groupService.getByGroupName("1")).thenReturn(groups);
        Assert.assertEquals(groups, groupService.getByGroupName("1"));
    }

    @Test
    void getGroupID() {
        Group group = Mockito.mock(Group.class);
        GroupService groupService = Mockito.mock(GroupService.class);
        Mockito.when(groupService.get(1)).thenReturn(group);
        Assert.assertEquals(group, groupService.get(1));
    }

    @Test
    void insertAccountGroupTrue() {
        Group group = Mockito.mock(Group.class);
        GroupService groupService = Mockito.mock(GroupService.class);
        Mockito.when(groupService.insertAccountGroup(group, 1)).thenReturn(true);
        Assert.assertEquals(true, groupService.insertAccountGroup(group, 1));
    }

    @Test
    void insertAccountGroupFalse() {
        Group group = Mockito.mock(Group.class);
        GroupService groupService = Mockito.mock(GroupService.class);
        Mockito.when(groupService.insertAccountGroup(group, 1)).thenReturn(false);
        Assert.assertEquals(false, groupService.insertAccountGroup(group, 1));
    }

}