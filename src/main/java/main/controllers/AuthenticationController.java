package main.controllers;

import main.controllers.util.LoginCredential;
import main.controllers.util.UserCredentials;
import main.controllers.util.ValidationStatus;
import main.security.util.OpenAMRestConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private static final String successLogin = "Success";
    private static final String failLogin = "Fail";
    private OpenAMRestConsumer openAMRestConsumer;
    private Base64.Encoder encoder = Base64.getEncoder();

    public AuthenticationController(){}

    @Autowired
    public AuthenticationController(OpenAMRestConsumer openAMRestConsumer) {
        this.openAMRestConsumer = openAMRestConsumer;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginCredential login(@RequestBody UserCredentials userCredentials, HttpServletResponse response)
            throws IOException {
        try {
            return new LoginCredential(openAMRestConsumer.loginUser(userCredentials.getUsername(),
                    encoder.encodeToString(userCredentials.getPassword().getBytes())));
        } catch (HttpClientErrorException e) {
            response.sendError(403, "Invalid credentials");
            return null;
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(OpenAMRestConsumer.cookieName)) {
                String tokenId = cookie.getValue();
                try {
                    openAMRestConsumer.logoutUserByToken(tokenId);
                    response.setStatus(200);
                    return;
                } catch (HttpClientErrorException e) {
                    response.sendError(400, "Invalid tokenId");
                    return;
                }
            }
        }
        response.sendError(400, "No tokenId in cookie");
    }

    @RequestMapping(path = "/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ValidationStatus validate(@RequestBody LoginCredential credential) {
        return new ValidationStatus(openAMRestConsumer.validateSessionByToken(credential.getTokenId()).isValid());
    }
}
