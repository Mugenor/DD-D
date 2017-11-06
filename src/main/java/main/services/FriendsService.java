package main.services;

import main.entities.User;
import main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FriendsService {
    private UserRepository userRepository;
    @Autowired
    public FriendsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public FriendsService(){}

    /**
     * Find and return all friends of user in database
     * @param user user which friends you want to get
     * @return list of friends
     */
    @Transactional
    public List<User> getAllFriends(User user){
        return userRepository.getAllFriends(user.getUsername());
    }

    /**
     * Find and return all friends of user in database
     * @param username user's username
     * @return list of friends
     */
    @Transactional
    public List<User> getAllFriends(String username){
        return userRepository.getAllFriends(username);
    }

    /**
     * Find and return all awaiting friends of user in database
     * @param user user which awaiting friends you want to get
     * @return list of awaiting friends
     */
    @Transactional
    public List<User> getAllAwaitingFriends(User user){
        return userRepository.getAllAwaitingFriends(user.getUsername());
    }

    /**
     * Find and return all awaiting friends of user in database
     * @param username user's username
     * @return list of awaiting friends
     */
    @Transactional
    public List<User> getAllAwaitingFriends(String username){
        return userRepository.getAllAwaitingFriends(username);
    }

    /**
     * Add to user's friend list a new friend in database
     * @param user user which accepted a new friend
     * @param friend user which want to be a friend of user
     * @return true - if success. false - if unsuccess
     */
    @Transactional
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

    /**
     * Remove friend from user's friend list in database
     * @param user user who remove a friend
     * @param friend friend which is removing
     * @return true - if success. false - if unsuccess
     */
    @Transactional
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

    /**
     * Adding a friend request to user from friend in database
     * @param user will have new request
     * @param friend user which requested to be friends
     * @return true - if success. false - if unsuccess
     */
    @Transactional
    public boolean addRequestForFriends(User user, User friend){
        if(user.getAwaitingFriends().add(friend)){
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * User reject a friend request from friend in database
     * @param user user who rejected request
     * @param friend user whose request were rejected
     * @return true - if success. false - if unsuccess
     */
    @Transactional
    public boolean rejectRequestForFriends(User user, User friend){
        if(user.getAwaitingFriends().remove(friend)){
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
