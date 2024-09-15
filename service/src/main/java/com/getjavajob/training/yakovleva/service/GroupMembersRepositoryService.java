package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.GroupMembers;
import com.getjavajob.training.yakovleva.repository.GroupMembersRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class GroupMembersRepositoryService {
    private static final Logger logger = LogManager.getLogger(GroupMembersRepositoryService.class);
    private final GroupMembersRepository groupMembersRepository;

    @Autowired
    public GroupMembersRepositoryService(GroupMembersRepository groupMembersRepository) {
        logger.info("GroupMembersRepositoryService(groupMembersRepository)");
        this.groupMembersRepository = groupMembersRepository;
    }

    public List<GroupMembers> getAll(Group group) {
        logger.info("getAll(group = {})", group);
        return groupMembersRepository.getMembersByGroup(group);
    }

    public List<GroupMembers> getAccountGroups(Account account, Pageable pageable) {
        logger.info("getAccountGroups(account.getId() = {}, pageable = {})", account.getId(), pageable);
        return groupMembersRepository.findAllByMember(account, pageable);
    }

    public long getSizeAccountGroups(int accountId) {
        logger.info("getSizeAccountGroups(accountId = {})", accountId);
        return groupMembersRepository.getCountOfGroup(accountId);
    }

    @Transactional
    public void saveAll(List<GroupMembers> groupMembers) {
        logger.info("saveAll(groupMembers.size = {})", groupMembers);
        groupMembersRepository.saveAll(groupMembers);
    }

}