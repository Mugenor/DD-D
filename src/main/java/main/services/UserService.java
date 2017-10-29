package main.services;

import main.entities.User;
import main.repositories.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService () {}

    @Autowired
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User getByLogin(String login){
        User user = userRepository.findOne(login);
        Hibernate.initialize(user.getFriends());
        Hibernate.initialize(user.getCardsCanUse());
        Hibernate.initialize(user.getCardsInUse());
        return user;
    }

    @Transactional
    public User getByUsername(String username){
        User user = userRepository.findByUsername(username);
        Hibernate.initialize(user.getFriends());
        Hibernate.initialize(user.getCardsCanUse());
        Hibernate.initialize(user.getCardsInUse());
        return user;
    }

    @Transactional
    public User getByStatus(int status){
        User user = userRepository.findByStatus(status);
        Hibernate.initialize(user.getFriends());
        Hibernate.initialize(user.getCardsCanUse());
        Hibernate.initialize(user.getCardsInUse());
        return user;
    }

    @Transactional
    public Iterable<User> getAllUsers(){
        Iterable<User> users = userRepository.findAll();
        for(User user: users){
            Hibernate.initialize(user.getFriends());
            Hibernate.initialize(user.getCardsCanUse());
            Hibernate.initialize(user.getCardsInUse());
        }
        return users;
    }

    @Transactional
    public User saveOrUpdate(User user){
        return userRepository.save(user);
    }
    @Transactional
    public void deleteByLogin(String login){
        userRepository.delete(login);
    }
    @Transactional
    public void delete(User user){
        userRepository.delete(user);
    }
}