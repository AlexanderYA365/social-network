package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.dao.RelationsDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RelationsServiceTest {
    @Mock
    private RelationsDao relationsDao;
    @InjectMocks
    private RelationsService relationsService;

    @Test
    void create() {
        Relations relations = new Relations();
        relations.setAccountId(1);
        relations.setFriendId(1);
        boolean actual = relationsService.create(relations);
    }

    @Test
    void getByAccountId() {
    }

    @Test
    void getByFriendId() {
    }

    @Test
    void update() {
        Relations relations = new Relations();
        relations.setAccountId(1);
        relations.setFriendId(1);
        relationsService.update(relations);
    }

    @Test
    void deleteByAccountId() {
    }
}