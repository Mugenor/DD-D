package main.controllers;

import main.entities.User;
import main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> getAllUsers(HttpServletRequest request){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/by-login/{login}", method = RequestMethod.GET)
    public User getUserByLogin(@PathVariable String login){
        return userService.getByLogin(login);
    }

    @RequestMapping(value = "/by-username/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable String username){
        return userService.getByUsername(username);
    }
}
