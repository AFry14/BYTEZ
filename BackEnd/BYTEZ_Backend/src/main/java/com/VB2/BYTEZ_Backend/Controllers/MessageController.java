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
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @DeleteMapping(path = "/messages/delete/{userNameSelf}/{userNameFriend}")
    public @ResponseBody String deleteChatHistory(@PathVariable("userNameSelf") String userNameSelf, @PathVariable("userNameFriend") String userNameFriend)
    {
        messageRepository.deleteByUserNameSelfAndUserNameFriend(userNameSelf, userNameFriend);
        messageRepository.deleteByUserNameSelfAndUserNameFriend(userNameFriend, userNameSelf);
        return "{\"Status\" : \"Success\"}";
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

        broadcast(getMessageHistory(userNameSelf, userNameFriend), userNameSelf);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        logger.info("Entered into message: Get message " + message);

        String userNameSelf = sessionUserMap.get(session);
        String userNameFriend = userUserMap.get(userNameSelf);

        messageRepository.save(new Message(userNameSelf, userNameFriend, message));

        broadcast(userNameSelf + " : " + message, null);

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

    private void broadcast(String message, String userName) {
        sessionUserMap.forEach((session, username) -> {
            try {
                if (userName == null || userName.equals(username))
                {
                    session.getBasicRemote().sendText(message);
                }
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
        messages.addAll(messageRepository.findByUserNameSelfAndUserNameFriend(usernameFriend, usernameSelf));
        messages.sort(Comparator.comparing(Message::getId));

        StringBuilder sb = new StringBuilder();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("MM/dd hh:mm a");

            for (Message m : messages)
            {
                String prettyDate = format.format(m.getSent());
                sb.append(prettyDate + " - " + m.getUserNameSelf() + " : " + m.getContent() + "\n");
            }

            sb.append("Now \n");
        return sb.toString();
    }
}
