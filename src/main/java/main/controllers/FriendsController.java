package main.controllers;

import main.entities.User;
import main.security.util.OpenAMRestConsumer;
import main.services.FriendsService;
import main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static main.web.socket.util.CookieParser.findCookie;

@RestController
@RequestMapping(path = "/friends", produces = MediaType.APPLICATION_JSON_VALUE)
public class FriendsController {
    private FriendsService friendsService;
    private UserService userService;
    private JavaMailSender sender;
    private OpenAMRestConsumer openAMRestConsumer;

    public FriendsController(){}

    @Autowired
    public FriendsController(FriendsService friendsService, UserService userService, JavaMailSender sender, OpenAMRestConsumer openAMRestConsumer) {
        this.friendsService = friendsService;
        this.userService = userService;
        this.sender = sender;
        this.openAMRestConsumer = openAMRestConsumer;
    }

    @RequestMapping(path = "/awaiting/{username}", method = RequestMethod.GET)
    public Iterable<User> getAllAwaitingFriends(@PathVariable String username){
        return friendsService.getAllAwaitingFriends(username);
    }

    @RequestMapping(path = "/all/{username}", method = RequestMethod.GET)
    public Iterable<User> getAllFriends(@PathVariable String username){
        return friendsService.getAllFriends(username);
    }

    @RequestMapping(path = "/add/{friendUsername}", method = RequestMethod.POST)
    public void requestFriends(@PathVariable String friendUsername, HttpServletResponse response, HttpServletRequest request)
            throws IOException, MessagingException {
        try{
            String tokenId = findCookie(request.getCookies(), OpenAMRestConsumer.cookieName);
            String username = openAMRestConsumer.validateSessionByToken(tokenId).getUid();
            if(!friendsService.addRequestForFriends(friendUsername, username)){
                response.sendError(400, "Can't do it");
            } else {
                User user = userService.getByUsername(friendUsername);
                if(user!=null){
                    sendMail(user.getMail(), user.getUsername() + ", хочет добавить тебя в друзья " + username +
                            "! Ответь ему " +
                            "<a href=\"http://website.mydomain.com:8080/\">здесь</a>!", "Новый запрос в друзья");
                }
            }
        } catch (HttpClientErrorException e){
            response.sendError(400, "Invalid tokenId");
        }
    }

    @RequestMapping(path = "/accept/{friendUsername}", method = RequestMethod.POST)
    public void acceptFriendsRequest(@PathVariable String friendUsername, HttpServletResponse response, HttpServletRequest request)
            throws IOException, MessagingException{
        try{
            String tokenId = findCookie(request.getCookies(), OpenAMRestConsumer.cookieName);
            String username = openAMRestConsumer.validateSessionByToken(tokenId).getUid();
            if(!friendsService.addFriendToUser(username, friendUsername)){
                response.sendError(400, "Can't do it");
            } else {
                User user = userService.getByUsername(friendUsername);
                if(user!=null){
                    sendMail(user.getMail(), username + " принял твой запрос в друзья!", "У тебя новый друг");
                }
            }
        } catch (HttpClientErrorException e){
            response.sendError(400, "Invalid tokenId");
        }
    }

    @RequestMapping(path = "/reject/{friendUsername}", method = RequestMethod.POST)
    public void rejectFriendsRequest(@PathVariable String friendUsername, HttpServletResponse response, HttpServletRequest request)
            throws IOException, MessagingException{
        try {
            String tokenId = findCookie(request.getCookies(), OpenAMRestConsumer.cookieName);
            String username = openAMRestConsumer.validateSessionByToken(tokenId).getUid();
            if(!friendsService.rejectRequestForFriends(username, friendUsername)){
                response.sendError(400, "Can't do it");
            } else {
                User user = userService.getByUsername(friendUsername);
                if(user!=null){
                    sendMail(user.getMail(), username + " отклонил твой запрос в друзья!", "Твой запрос в друзья отклонен");
                }
            }
        } catch (HttpClientErrorException e){
            response.sendError(400, "Invalid tokenId");
        }
    }

    @RequestMapping(path = "/remove/{friendUsername}", method = RequestMethod.POST)
    public void removeFriend(@PathVariable String friendUsername, HttpServletRequest request, HttpServletResponse response)
            throws IOException, MessagingException{
        try{
            String tokenId = findCookie(request.getCookies(), OpenAMRestConsumer.cookieName);
            String username = openAMRestConsumer.validateSessionByToken(tokenId).getUid();
            if(!friendsService.removeFriendFromUser(username, friendUsername)){
                response.sendError(400, "Can't do it");
            } else {
                User user = userService.getByUsername(friendUsername);
                if(user!=null){
                    sendMail(user.getMail(), username + " удалил вас из своего списка друзей!", "Вы лишились друга");
                }
            }
        } catch (HttpClientErrorException e){
            response.sendError(400, "Invalid tokenId");
        }
    }


    private void sendMail(String mail, String text, String subject) throws MessagingException{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(mail);
        helper.setText(text, true);
        helper.setSubject(subject);

        sender.send(message);
    }
}
