package com.getjavajob.training.yakovleva.repository;

import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.utilsEnum.MessageType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Integer>,
        PagingAndSortingRepository<Message, Integer> {
    List<Message> getMessagesBySenderId(int senderId);

    List<Message> getMessagesByReceiverIdAndSenderIdOrReceiverIdAndSenderIdAndMessageTypeOrderByPublicationDate(
            int receiverId, int senderId, int secondSenderId, int secondReceiverId, MessageType messageType);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.senderId=:senderId")
    long getCountOfMessagesAccount(@Param("senderId") int senderId);

    List<Message> findAllByIdIn(int[] message);

    Message findTopBySenderIdAndReceiverIdAndMessageTypeOrderByPublicationDateDesc(int senderId, int receiverId,
                                                                                   MessageType messageType);

    List<Message> getMessagesByReceiverIdAndMessageTypeOrderByPublicationDateDesc(int receiverId,
                                                                                  MessageType messageType,
                                                                                  Pageable pageable);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.receiverId=:receiverId AND m.messageType=:messageType")
    long getCountMessagesByReceiverIdAndMessageType(int receiverId, MessageType messageType);

    @Transactional
    Message save(Message message);

    @Transactional
    Message save(List<Message> messages);

    @Transactional
    void deleteById(int id);

    Message getMessageById(int messageId);

}