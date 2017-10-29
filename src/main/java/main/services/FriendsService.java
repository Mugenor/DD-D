package main.services;

import main.entities.User;
import main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class FriendsService {
    private UserRepository userRepository;
    @Autowired
    public FriendsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public FriendsService(){}
    @Transactional
    public Collection<User> getAllFriends(User user){
        return userRepository.getAllFriends(user.getUsername());
    }
    @Transactional
    public Collection<User> getAllFriendsByUsername(String username){
        return userRepository.getAllFriends(username);
    }
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
    @Transactional
    public boolean addRequestForFriends(User user, User friend){
        if(user.getAwaitingFriends().add(friend)){
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
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
