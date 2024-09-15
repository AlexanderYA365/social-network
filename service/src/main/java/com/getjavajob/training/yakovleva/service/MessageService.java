package com.getjavajob.training.yakovleva.service;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.common.utilsEnum.MessageType;
import com.getjavajob.training.yakovleva.dao.RelationsDao;
import com.getjavajob.training.yakovleva.repository.MessageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class MessageService {
    private static final Logger logger = LogManager.getLogger(MessageService.class);
    private MessageRepository messageRepository;
    private RelationsDao relationsDao;

    @Autowired
    public MessageService(MessageRepository messageRepository, RelationsDao relationsDao) {
        logger.info("MessageService(MessageDao messageDao)");
        this.messageRepository = messageRepository;
        this.relationsDao = relationsDao;
    }

    public MessageService() {
    }

    public List<Message> getMessagesByAccountIdAndReceiverIdAndMessageType(int accountId, int receiverId,
                                                                           MessageType messageType) {
        logger.info("getMessagesByAccountIdAndReceiverIdAndMessageType(accountId = {}, receiverId = {}," +
                "messageType = {}", accountId, receiverId, messageType);
        return messageRepository.
                getMessagesByReceiverIdAndSenderIdOrReceiverIdAndSenderIdAndMessageTypeOrderByPublicationDate(
                        accountId, receiverId, receiverId, accountId, MessageType.PRIVATE);
    }

    public List<Message> getUniqueMessages(Account account, Pageable pageable) {
        logger.info("getUniqueMessages(account = {}, pageable = {})", account, pageable);
        List<Message> messages = new ArrayList<>();
        List<Relations> relations = relationsDao.getFriendsAccount(account.getId(),
                pageable.getPageNumber(),
                pageable.getPageSize());
        for (Relations relation : relations) {
            logger.info("relation = {}", relation);
            messages.add(messageRepository.
                    findTopBySenderIdAndReceiverIdAndMessageTypeOrderByPublicationDateDesc(relation.getAccountId(),
                            relation.getFriendId(), MessageType.PRIVATE));
        }
        messages.removeIf(Objects::isNull);
        messages.sort((o1, o2) -> o2.getPublicationDate().compareTo(o1.getPublicationDate()));
        return messages;
    }

    public long getSizeUniqueMessages(Account account) {
        logger.info("getSizeUniqueMessages(account = {})", account);
        List<Message> messages = new ArrayList<>();
        List<Relations> relations = relationsDao.getByAccountId(account.getId());
        for (Relations relation : relations) {
            messages.add(messageRepository.
                    findTopBySenderIdAndReceiverIdAndMessageTypeOrderByPublicationDateDesc(relation.getAccountId(),
                            relation.getFriendId(), MessageType.PRIVATE));
        }
        messages.removeIf(Objects::isNull);
        messages.sort((o1, o2) -> o2.getPublicationDate().compareTo(o1.getPublicationDate()));
        return messages.size();
    }

    public List<Message> getWallMassageAccount(Account account, Pageable pageable) {
        logger.info("getWallMassageAccount(account = {}, pageable = {})", account, pageable);
        return messageRepository.getMessagesByReceiverIdAndMessageTypeOrderByPublicationDateDesc(account.getId(),
                MessageType.WALL, pageable);
    }

    public List<Message> getWallMassagesGroup(Group group, Pageable pageable) {
        logger.info("getWallMassagesGroup(group = {}, pageable = {})", group, pageable);
        return messageRepository.getMessagesByReceiverIdAndMessageTypeOrderByPublicationDateDesc(group.getGroupId(),
                MessageType.GROUP, pageable);
    }

    public long getSizeWallMassages(Group group) {
        logger.info("getSizeWallMassages(group = {})", group);
        return messageRepository.getCountMessagesByReceiverIdAndMessageType(group.getGroupId(), MessageType.WALL);
    }

    public long getSizeWallMassages(Account account) {
        logger.info("getSizeWallMassages(account = {})", account);
        return messageRepository.getCountMessagesByReceiverIdAndMessageType(account.getId(), MessageType.WALL);
    }

    @Transactional
    public Message createMassage(Message message) {
        logger.info("createMassage(message = {})", message);
        return messageRepository.save(message);
    }

    @Transactional
    public boolean delete(int id) {
        logger.info("delete(id = {})", id);
        messageRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void createMessages(List<Message> messages) {
        logger.info("createMessages(messages.length = {})", messages.size());
        for (Message message : messages) {
            messageRepository.save(message);
        }
    }

}