package main.services;

import main.entities.User;
import main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendsService {
    private UserRepository userRepository;
    @Autowired
    public FriendsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public FriendsService(){}

    /**
     * Find and return the list of awaitingFriends by User from the database
     * @param user of required User
     * @return list of awaitingFriends
     */
    public List<User> getAllAwaitingFriends(User user){
        return userRepository.findAwaitingFriendsByUsername(user.getUsername());
    }

    /**
     * Find and return the list of awaitingFriends by User from the database
     * @param username of required User
     * @return list of awaitingFriends
     */
    public List<User> getAllAwaitingFriends(String username){
        return userRepository.findAwaitingFriendsByUsername(username);
    }


    /**
     * Return all Friends from the database
     * @return thelist of friends
     */
    public List<User> getAllFriends(String username){
        return userRepository.findFriendsByUsername(username);
    }


    /**
     * Add in user's friends a new friend
     * @param user who accepted a new friend
     * @param friend who want to be a friend for required user
     * @return true if successfully, false if unsuccessfully
     */
    public boolean addFriendToUser(User user, User friend){
        if(user.getAwaitingFriends().remove(friend)) {
            if (user.getFriends().add(friend)) {
                if (friend.getFriends().add(user)) {
                    userRepository.save(user);
                    userRepository.save(friend);
                    return true;
                } else {

                    user.getAwaitingFriends().add(friend);
                    user.getFriends().remove(friend);
                    return false;
                }
            } else {
                user.getAwaitingFriends().add(friend);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean addFriendToUser(String username, String friendsUsername){
        User user = userRepository.findByUsername(username);
        User friend = userRepository.findByUsername(friendsUsername);
        return user!=null && friend!=null && addFriendToUser(user, friend);
    }

    /**
     * Remove friend from user's friends
     * @param user who remove a friend
     * @param friend which will be removed
     * @return true if successfully, false if unsuccessfully
     */
    public boolean removeFriendFromUser(User user, User friend){
        if(user.getFriends().remove(friend)){
            if(friend.getFriends().remove(user)) {
                userRepository.save(user);
                userRepository.save(friend);
                return true;
            } else {
                user.getFriends().add(friend);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean removeFriendFromUser(String username, String friendsUsername){
        User user = userRepository.findByUsername(username);
        User friend = userRepository.findByUsername(friendsUsername);
        return user!=null && friend!=null && removeFriendFromUser(user, friend);
    }
    /**
     * Add a friend request to user from new friend
     * @param user who have new request
     * @param friend which requested in friends
     * @return true if successfully, false if unsuccessfully
     */
    public boolean addRequestForFriends(User user, User friend){
        if(user.getAwaitingFriends().add(friend)){
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean addRequestForFriends(String username, String friendsUsername){
        User user = userRepository.findByUsername(username);
        User friend = userRepository.findByUsername(friendsUsername);
        return user!=null && friend!=null && addRequestForFriends(user, friend);
    }
    /**
     * User reject a request from friend
     * @param user who rejected request
     * @param friend whose request were rejected
     * @return true if successfully, false if unsuccessfully
     */
    public boolean rejectRequestForFriends(User user, User friend){
        if(user.getAwaitingFriends().remove(friend)){
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    public boolean rejectRequestForFriends(String username, String friendsUsername){
        User user = userRepository.findByUsername(username);
        User friend = userRepository.findByUsername(friendsUsername);
        return user!=null && friend!=null && rejectRequestForFriends(user, friend);
    }
}
