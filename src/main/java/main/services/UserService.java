package main.services;

import main.entities.User;
import main.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    public UserService () {}

    @Autowired
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Find and return one User by login from the database
     * @param login of required User
     * @return one User
     */
    public User getByLogin(String login){
        User user = userRepository.findOne(login);
        if(user!=null) {
            Hibernate.initialize(user.getFriends());
            Hibernate.initialize(user.getCardsCanUse());
            Hibernate.initialize(user.getCardsInUse());
        }
        return user;
    }

    /**
     * Find and return one User by username from the database
     * @param username of required User
     * @return one User
     */
    public User getByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user!=null) {
            Hibernate.initialize(user.getFriends());
            Hibernate.initialize(user.getCardsCanUse());
            Hibernate.initialize(user.getCardsInUse());
        }
        return user;
    }

    /**
     * Find and return all Users by status from the database
     * @param status of required Users
     * @return Users
     */
    public List<User> getByStatus(int status){
        List<User> users = userRepository.findByStatus(status);
        for (User user : users) {
            Hibernate.initialize(user.getFriends());
            Hibernate.initialize(user.getCardsCanUse());
            Hibernate.initialize(user.getCardsInUse());
        }
        return users;
    }

    /**
     * Return all Users without friends, awaitingFriends, cardsCanUse and cardsInUse from the database
     * @return Users with minimum information
     */
    public Iterable<User> getAllUsers(){
        Iterable<User> users = userRepository.findAll();
        for(User user: users){
            Hibernate.initialize(user.getFriends());
            Hibernate.initialize(user.getCardsCanUse());
            Hibernate.initialize(user.getCardsInUse());
        }
        return users;
    }

    /**
     * Save the User if it's a new or update if it already exists
     * @param user which should be saved or updated
     * @return saved or updated User
     */
    public User saveOrUpdate(User user){
        return userRepository.save(user);
    }

    /**
     * Delete the User by login from the database
     * @param login of required User
     */
    public void deleteByLogin(String login){
        userRepository.delete(login);
    }

    /**
     * Delete the User by username from the database
     * @param username of required User
     */
    public void deleteByUsername(String username){
        userRepository.deleteByUsername(username);
    }

    /**
     * Delete the required User from the database
     * @param user which should be deleted
     */
    public void delete(User user){
        userRepository.delete(user);
    }
}