package main.controllers;

import main.entities.User;
import main.services.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/friends", produces = MediaType.APPLICATION_JSON_VALUE)
public class FriendsController {
    @Autowired
    private FriendsService friendsService;

    @RequestMapping(path = "/awaiting/{username}", method = RequestMethod.GET)
    public Iterable<User> getAllAwaitingFriends(@PathVariable String username){
        return friendsService.getAllAwaitingFriends(username);
    }

    @RequestMapping(path = "/all/{username}", method = RequestMethod.GET)
    public Iterable<User> getAllFriends(@PathVariable String username){
        return friendsService.getAllFriends(username);
    }
}
