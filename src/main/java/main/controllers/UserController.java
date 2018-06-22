package main.controllers;

import main.entities.User;
import main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;

    public UserController() {}

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/search/{username}", method = RequestMethod.GET)
    public Set<User> getUserByUsernameLike(@PathVariable java.lang.String username){
        return userService.getByUsernameLike(username);
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable java.lang.String username){
        return userService.getByUsername(username);
    }

}
