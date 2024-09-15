package com.getjavajob.training.yakovleva.web.config;

import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.utils.MessageDecoder;
import com.getjavajob.training.yakovleva.common.utils.MessageEncoder;
import com.getjavajob.training.yakovleva.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Component
@ServerEndpoint(value = "/goChat/{username}",
        configurator = SpringContext.class,
        encoders = MessageEncoder.class,
        decoders = MessageDecoder.class)
public class ChatEndpoint {
    private static final Logger logger = LogManager.getLogger(ChatEndpoint.class);
    private static final List<Session> sessions = new LinkedList<>();
    private final MessageService messageService;
    private Session session;

    @Autowired
    public ChatEndpoint(MessageService messageService) {
        logger.info("ChatEndpoint()");
        this.messageService = messageService;
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("onOpen(session = {})", session);
        this.session = session;
        session.setMaxTextMessageBufferSize(10_000_000);
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        logger.info("onMessage(accountMessage.type = {})", message.getMessageType());
        sessions.forEach(s -> {
                    if (s == this.session) return;
                    try {
                        s.getBasicRemote().sendObject(message);
                    } catch (IOException | EncodeException e) {
                        logger.error("Error in onMessage() - exception = {}", e);
                    }
                }
        );
        messageService.createMassage(message);
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("onClose(session = {})", session);
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("onError(throwable = {})", throwable);
        throwable.printStackTrace();
    }

}