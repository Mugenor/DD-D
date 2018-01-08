package main.security.util;

import main.security.util.entities.LoginResponse;
import main.security.util.entities.LogoutResponse;
import main.security.util.entities.ValidateResponse;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class OpenAMRestConsumer {
    private static final String authenticationURI = "/json/authenticate";
    private static final String sessionsURI = "/json/sessions";
    private static final String logoutAction = "/?_action=logout";
    private static final String validateAction = "?_action=validate";
    private static final String usernameHeader = "X-OpenAM-Username";
    private static final String passwordHeader = "X-OpenAM-Password";
    public static final String cookieName = "iplanetDirectoryPro";

    private String openAMDomainName;

    public OpenAMRestConsumer(String openAMDomainName){
        this.openAMDomainName = openAMDomainName;
        System.out.println("---------- " + openAMDomainName + " -------------");
    }

    public String loginUser(String login, String password) throws HttpClientErrorException{
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(usernameHeader, login);
        headers.set(passwordHeader, password);

        HttpEntity<LoginResponse> entity = new HttpEntity<>(headers);

        ResponseEntity<LoginResponse> response = template.exchange(openAMDomainName + authenticationURI,
                    HttpMethod.POST, entity, LoginResponse.class);
        return response.getBody().getTokenId();
    }

    public boolean logoutUserByToken(String tokenId) throws HttpClientErrorException{
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(cookieName, tokenId);

        HttpEntity<LogoutResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<LogoutResponse> response = template.exchange(openAMDomainName + sessionsURI + logoutAction,
                HttpMethod.POST, entity, LogoutResponse.class);
        return response.getBody().getResult().startsWith("Success");
    }

    public ValidateResponse validateSessionByToken(String tokenId){
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ValidateResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<ValidateResponse> response = template.exchange(openAMDomainName + sessionsURI + "/" + tokenId + validateAction,
                HttpMethod.POST, entity, ValidateResponse.class);
        return response.getBody();
    }
}
