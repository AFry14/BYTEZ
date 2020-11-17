package com.VB2.BYTEZ_Backend.Service;

import com.VB2.BYTEZ_Backend.Domain.Friendship;
import com.VB2.BYTEZ_Backend.Domain.Restaurant;
import com.VB2.BYTEZ_Backend.Domain.Review;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.FriendshipRepository;
import com.VB2.BYTEZ_Backend.Repositories.RestaurantRepository;
import com.VB2.BYTEZ_Backend.Repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private ReviewRepository reviewRepository;

   @Autowired
   private RestaurantRepository restaurantRepository;

   @Autowired
   private FriendshipRepository friendshipRepository;

   public List<User> getAllUsers()
   {
      return userRepository.findAll();
   }

   public User getUser(Long id)
   {
      return userRepository.findById(id).isPresent() ?
              userRepository.findById(id).get() : null;
   }

   public User getByUsername(String userName)
   {
       return userRepository.findByUserName(userName).isPresent() ?
               userRepository.findByUserName(userName).get() : null;
   }

   public User loginUser(String email, String password)
   {
    return userRepository.findByEmailAndPassword(email, password).isPresent() ?
            userRepository.findByEmailAndPassword(email, password).get() : null;
   }

    public List<Review> getAllReviewsByUser(Long id)
    {
        return reviewRepository.findByAuthorId(id);
    }

    public Restaurant getUserRestaurant(Long id)
    {
        return restaurantRepository.findByOwnerId(id);
    }

    public List<User> getFriends(Long id)
    {
        // Create return list
        List<User> ret = new ArrayList<>();
        // For each friend or request you might have
        for (Friendship f: friendshipRepository.findAllBySelfId(id))
        {
            // Get friend id
            Long friendId = f.getFriend().getId();
            // For each friendship or request your friend might have
            for (Friendship s : friendshipRepository.findAllBySelfId(friendId))
            {
                if (s.getFriend().getId() == id)
                {
                    ret.add(f.getFriend());
                }
            }
        }
        return ret;
    }

    public List<User> getFriendRequests(Long id)
    {
        // Create return list
        List<User> ret = new ArrayList<>();

        for (Friendship f : friendshipRepository.findAllByFriendId(id))
        {
            Long friendId = f.getSelf().getId();
            if (friendshipRepository.findBySelfIdAndFriendId(id, friendId) == null)
            {
                ret.add(f.getSelf());
            }
        }

        return ret;
    }

   public String registerUser(User user)
   {
      userRepository.save(user);
      return "{\"status\":\"Success\"}";
   }

   public User updateUserUserName(Long id, String newUserName)
   {
      return userRepository.findById(id)
              .map(user -> {
                 user.setUserName(newUserName);
                 return userRepository.save(user);
              })
              .orElse(null);
   }

   public User updateUserInfo(Long id, User newUser)
   {
      return userRepository.findById(id)
              .map(user -> {
                 user.setUserName(newUser.getUserName());
                 user.setFirstName(newUser.getFirstName());
                 user.setLastName(newUser.getLastName());
                 user.setEmail(newUser.getEmail());
                 user.setPassword(newUser.getPassword());
                 user.setUserType(newUser.getUserType());
                 user.setFavoriteDrink(newUser.getFavoriteDrink());
                 user.setFavoriteFood(newUser.getFavoriteFood());
                 user.setFavoriteRestaurant(newUser.getFavoriteRestaurant());
                 return userRepository.save(user);
              })
              .orElse(null);
   }

   public String deleteUser(Long id)
   {
      userRepository.deleteById(id);
      return "{\"status\":\"Success\"}";
   }
}
