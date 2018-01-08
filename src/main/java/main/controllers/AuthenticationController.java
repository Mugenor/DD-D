package main.controllers;

import main.controllers.util.LoginResponse;
import main.controllers.util.UserCredentials;
import main.security.util.OpenAMRestConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private static final String successLogin = "Success";
    private static final String failLogin = "Fail";

    @Value("${openam.domain}")
    private String openamDomain;

    private OpenAMRestConsumer openAMRestConsumer;

    @PostConstruct
    public void init(){
        System.out.println("AUTHController.openamDomain: " + openamDomain);
        openAMRestConsumer = new OpenAMRestConsumer(openamDomain);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse login(@RequestBody UserCredentials userCredentials){
        try {
            String tokenId = openAMRestConsumer.loginUser(userCredentials.getLogin(), userCredentials.getPassword());
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
                if(openAMRestConsumer.logoutUserByToken(tokeId)){
                    response.setStatus(200);
                    return;
                } else {
                    response.sendError(400, "Invalid tokenId");
                    return;
                }
            }
        }
        response.sendError(400, "No tokenId in cookie");
    }
}
