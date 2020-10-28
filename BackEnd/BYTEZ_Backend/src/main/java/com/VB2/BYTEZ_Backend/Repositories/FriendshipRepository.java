package com.VB2.BYTEZ_Backend.Repositories;

import com.VB2.BYTEZ_Backend.Domain.Friendship;
import com.VB2.BYTEZ_Backend.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    public List<Friendship> findAllBySelfId(Long id);
    public List<Friendship> findAllByFriendId(Long id);
    public void deleteBySelfIdAndFriendId(Long self_id, Long friend_id);
    public Friendship findBySelfIdAndFriendId(Long self_id, Long friend_id);
}
