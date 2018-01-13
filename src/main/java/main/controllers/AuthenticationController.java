package main.controllers;

import main.controllers.util.LoginResponse;
import main.controllers.util.UserCredentials;
import main.controllers.util.ValidationStatus;
import main.security.util.OpenAMRestConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static main.Application.openamDomain;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private static final String successLogin = "Success";
    private static final String failLogin = "Fail";

    private OpenAMRestConsumer openAMRestConsumer;
    private Base64.Encoder encoder = Base64.getEncoder();

    @PostConstruct
    public void init(){
        openAMRestConsumer = new OpenAMRestConsumer(openamDomain);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@RequestBody UserCredentials userCredentials){
        try {
            String tokenId = openAMRestConsumer.loginUser(userCredentials.getUsername(),
                                    encoder.encodeToString(userCredentials.getPassword().getBytes()));
            return new LoginResponse(successLogin, tokenId);
        }catch (HttpClientErrorException e){
            return new LoginResponse(failLogin, null);
        }
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response)throws IOException{
        List<Cookie> cookies = Arrays.asList(request.getCookies());
        for(Cookie cookie: cookies){
            if(cookie.getName().equals(OpenAMRestConsumer.cookieName)){
                String tokeId = cookie.getValue();
                try{
                    openAMRestConsumer.logoutUserByToken(tokeId);
                    response.setStatus(200);
                    return;
                } catch (HttpClientErrorException e){
                    response.sendError(400, "Invalid tokenId");
                    return;
                }
            }
        }
        response.sendError(400, "No tokenId in cookie");
    }

    @RequestMapping(path = "/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ValidationStatus validate(@RequestBody String tokenId) throws IOException {
        System.out.println(tokenId);
        return new ValidationStatus(openAMRestConsumer.validateSessionByToken(tokenId).isValid());
    }
}
