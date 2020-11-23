package com.VB2.BYTEZ_Backend.Repositories;

import com.VB2.BYTEZ_Backend.Domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>
{
    List<Message> findByUserNameSelfAndUserNameFriend(String userNameSelf, String userNameFriend);

    @Modifying
    @Transactional
    @Query("delete from Message m where m.userNameSelf= :userNameSelf and m.userNameFriend= :userNameFriend")
    void deleteByUserNameSelfAndUserNameFriend(@Param("userNameSelf") String userNameSelf, @Param("userNameFriend") String userNameFriend);

}
