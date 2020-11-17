package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.Message;
import com.VB2.BYTEZ_Backend.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@ServerEndpoint(value = "/chat/{userNameSelf}/{userNameFriend}")
public class MessageController
{
    private static MessageRepository messageRepository;

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private static Map<Session, String> sessionUserMap = new Hashtable<>();
    private static Map<String, Session> userSessionMap = new Hashtable<>();
    private static Map<String, String> userUserMap = new Hashtable<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userNameSelf") String userNameSelf, @PathParam("userNameFriend") String userNameFriend) throws IOException
    {
        logger.info("Entered into open");

        sessionUserMap.put(session, userNameSelf);
        userSessionMap.put(userNameSelf, session);
        userUserMap.put(userNameSelf, userNameFriend);

        broadcast(getMessageHistory(userNameSelf, userNameFriend));
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        logger.info("Entered into message: Get message " + message);

        String userNameSelf = sessionUserMap.get(session);
        String userNameFriend = userUserMap.get(userNameSelf);

        messageRepository.save(new Message(userNameSelf, userNameFriend, message));

        broadcast(userNameSelf + " : " + message);

    }

    @OnClose
    public void onClose(Session session) throws IOException
    {
        logger.info("Entered Close");

        String userNameSelf = sessionUserMap.get(session);
        sessionUserMap.remove(session);
        userSessionMap.remove(userNameSelf);
        userUserMap.remove(userNameSelf);

        // Broadcast a messsage?
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }

    private void broadcast(String message) {
        sessionUserMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }
        });
    }

    private String getMessageHistory(String usernameSelf, String usernameFriend)
    {
        List<Message> messages = messageRepository.findByUserNameSelfAndUserNameFriend(usernameSelf, usernameFriend);
        List<Message> messages2 = messageRepository.findByUserNameSelfAndUserNameFriend(usernameFriend, usernameSelf);
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            for (Message m : messages)
            {

                sb.append(m.getSent().toString() + m.getUserNameSelf() + " : " + m.getContent() + "\n");
            }
            for (Message m : messages2)
            {
                sb.append(m.getSent().toString() + m.getUserNameSelf() + " : " + m.getContent() + "\n");
            }
            sb.append("Now \n");
        return sb.toString();
    }


}
