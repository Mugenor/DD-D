package main.controllers;

import main.controllers.util.FeedbackRequest;
import main.entities.Feedback;
import main.entities.User;
import main.security.util.OpenAMRestConsumer;
import main.services.FeedbackService;
import main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static main.web.socket.util.CookieParser.findCookie;

@RestController
@RequestMapping(path = "/feedback")
public class FeedbackController {
    private FeedbackService feedbackService;
    private UserService userService;
    private OpenAMRestConsumer openAMRestConsumer;

    public FeedbackController() {}

    @Autowired
    public FeedbackController(FeedbackService feedbackService, UserService userService, OpenAMRestConsumer openAMRestConsumer) {
        this.feedbackService = feedbackService;
        this.userService = userService;
        this.openAMRestConsumer = openAMRestConsumer;
    }

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public void postFeedback(@RequestBody FeedbackRequest feedbackRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        if(feedbackRequest == null || feedbackRequest.getMessage()==null){
           response.sendError(400,"Message must not be null.");
           return;
        }
        try {
            String username = openAMRestConsumer.validateSessionByToken
                    (findCookie(request.getCookies(), OpenAMRestConsumer.cookieName)).getUid();
            User user = userService.getByUsername(username);
            if(user!=null) {
                Feedback feedback = new Feedback();
                feedback.setUser(user);
                feedback.setCard(feedbackRequest.isCard());
                feedback.setDate(new Date());
                feedback.setMessage(feedbackRequest.getMessage());
                feedbackService.saveOrUpdate(feedback);
            } else {
                response.sendError(403, "Unauthorized");
            }
        } catch (HttpClientErrorException e){
            response.sendError(403, "Unauthorized");
        }
    }
}
