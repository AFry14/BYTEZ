package com.VB2.BYTEZ_Backend.WebSocket;

import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import com.VB2.BYTEZ_Backend.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

@ServerEndpoint("/websocket/{user}")
@Component
public class WebSocket
{
   
    private static Map<Session, String> sessionUserMap = new Hashtable<>();
    private static Map<String, Session> userSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String user)
    {
        logger.info("Entered into Open");

        sessionUserMap.put(session, user);
        userSessionMap.put(user, session);
        String message = "" + user + " has joined the chat";
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException
    {
        logger.info("Got messsage " + message);
        String user = sessionUserMap.get(session);

        if (message.startsWith("@"))
        {
            // Do something
        }
        else
        {
            broadcast(user + ": " + message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException
    {
        logger.info("Entered into close");

        String user = sessionUserMap.get(session);
        sessionUserMap.remove(session);
        userSessionMap.remove(user);

        String message = user + " disconnected";
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable)
    {
        // Do error handling here
        logger.info("Entered error");
    }

    private void broadcast(String message)
    {
        sessionUserMap.forEach((session, user)->{
            try{
               session.getBasicRemote().sendText(message);
            } catch (IOException e)
            {
                logger.info("Exeption: " + e.getMessage().toString());
                e.printStackTrace();
            }
        });
    }


}
