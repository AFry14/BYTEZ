package com.VB2.BYTEZ_Backend.Domain;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"self_id", "friend_id"})})
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "self_id", referencedColumnName = "id")
    private User self;

    @OneToOne
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private User friend;

    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSelf() {
        return self;
    }

    public void setSelf(User self) {
        this.self = self;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
