package com.VB2.BYTEZ_Backend.Domain;

import com.sun.istack.NotNull;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Message
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userNameSelf;

    @NotNull
    private String userNameFriend;

    @NotNull
    private String content;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date sent = new Date();

    public Message()
    {
    }

    public Message(String userNameSelf, String userNameFriend, String content)
    {
        this.userNameSelf = userNameSelf;
        this.userNameFriend = userNameFriend;
        this.content = content;
    }

    // Getters and Setters

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUserNameSelf()
    {
        return userNameSelf;
    }

    public void setUserNameSelf(String userNameSelf)
    {
        this.userNameSelf = userNameSelf;
    }

    public String getUserNameFriend()
    {
        return userNameFriend;
    }

    public void setUserName(String userNameFriend)
    {
        this.userNameFriend = userNameFriend;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getSent()
    {
        return sent;
    }

    public void setSent(Date sent)
    {
        this.sent = sent;
    }
}
