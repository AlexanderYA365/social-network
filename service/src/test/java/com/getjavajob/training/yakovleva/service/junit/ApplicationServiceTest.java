package com.getjavajob.training.yakovleva.service.junit;

import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class ApplicationServiceTest {

    @Test
    void createTrue() {
        Application application = Mockito.mock(Application.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Mockito.when(applicationService.create(application)).thenReturn(true);
        Assert.assertTrue(applicationService.create(application));
    }

    @Test
    void createFalse() {
        Application application = Mockito.mock(Application.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Mockito.when(applicationService.create(application)).thenReturn(false);
        Assert.assertFalse(applicationService.create(application));
    }

    @Test
    void updateTrue() {
        Application application = Mockito.mock(Application.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Mockito.when(applicationService.update(application)).thenReturn(true);
        Assert.assertTrue(applicationService.update(application));
    }

    @Test
    void updateFalse() {
        Application application = Mockito.mock(Application.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Mockito.when(applicationService.update(application)).thenReturn(false);
        Assert.assertFalse(applicationService.update(application));
    }

    @Test
    void deleteTrue() {
        Application application = Mockito.mock(Application.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Mockito.when(applicationService.delete(application)).thenReturn(true);
        Assert.assertTrue(applicationService.delete(application));
    }

    @Test
    void deleteFalse() {
        Application application = Mockito.mock(Application.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Mockito.when(applicationService.delete(application)).thenReturn(false);
        Assert.assertFalse(applicationService.delete(application));
    }

    @Test
    void read() {
        Application application = Mockito.mock(Application.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Mockito.when(applicationService.get(1)).thenReturn(application);
        Assert.assertEquals(application, applicationService.get(1));
    }

    @Test
    void readGroupAccount() {
        Application application = Mockito.mock(Application.class);
        Group group = Mockito.mock(Group.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Mockito.when(applicationService.getGroupAccount(group, 1)).thenReturn(application);
        Assert.assertEquals(application, applicationService.getGroupAccount(group, 1));
    }

    @Test
    void readAccount() {
        Application application = Mockito.mock(Application.class);
//        Friend friend = Mockito.mock(Friend.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        //Mockito.when(applicationService.getAccount(friend)).thenReturn(application);
        //Assert.assertEquals(application, applicationService.getAccount(friend));
    }

    @Test
    void readAllApplication() {
        List application = Mockito.mock(List.class);
        ApplicationService applicationService = Mockito.mock(ApplicationService.class);
        Mockito.when(applicationService.getAllApplication()).thenReturn(application);
        Assert.assertEquals(application, applicationService.getAllApplication());
    }
}