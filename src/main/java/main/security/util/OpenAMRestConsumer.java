package main.security.util;

import main.security.util.entities.LoginResponse;
import main.security.util.entities.LogoutResponse;
import main.security.util.entities.ValidateResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OpenAMRestConsumer {
    private static final String authenticationURI = "/json/authenticate";
    private static final String sessionsURI = "/json/sessions";
    private static final String logoutAction = "/?_action=logout";
    private static final String validateAction = "?_action=validate";
    private static final String usernameHeader = "X-OpenAM-Username";
    private static final String passwordHeader = "X-OpenAM-Password";
    public static final String cookieName = "iplanetDirectoryPro";
    private static final Logger logger = Logger.getLogger(OpenAMRestConsumer.class);

    private String openAMDomainName;

    public OpenAMRestConsumer(@Value("${openam.domain}") String openAMDomainName){
        this.openAMDomainName = openAMDomainName;
        System.out.println("---------- " + openAMDomainName + " -------------");
    }

    public String loginUser(String username, String password) throws HttpClientErrorException{
        RestTemplate template = new RestTemplate();
        System.out.println("Login username=" + username + ", password=" + password);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(usernameHeader, username);
        headers.set(passwordHeader, password);

        HttpEntity<LoginResponse> entity = new HttpEntity<>(headers);
        System.out.println(openAMDomainName + authenticationURI);
        ResponseEntity<LoginResponse> response = template.exchange(openAMDomainName + authenticationURI,
                    HttpMethod.POST, entity, LoginResponse.class);
        System.out.println("Token:" + response.getBody().getTokenId());
        return response.getBody().getTokenId();
    }

    public boolean logoutUserByToken(String tokenId) throws HttpClientErrorException{
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(cookieName, tokenId);

        HttpEntity<LogoutResponse> entity = new HttpEntity<>(headers);
        System.out.println("Token:" + tokenId);
        ResponseEntity<LogoutResponse> response = template.exchange(openAMDomainName + sessionsURI + logoutAction,
                HttpMethod.POST, entity, LogoutResponse.class);
        return response.getBody().getResult().startsWith("Success");
    }

    public ValidateResponse validateSessionByToken(String tokenId){
        RestTemplate template = new RestTemplate();
        System.out.println("Validating token:" + tokenId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ValidateResponse> entity = new HttpEntity<>(headers);
        ResponseEntity<ValidateResponse> response = template.exchange(openAMDomainName + sessionsURI + "/" + tokenId + validateAction,
                HttpMethod.POST, entity, ValidateResponse.class);
        System.out.println("Url: " + openAMDomainName + sessionsURI + "/" + tokenId + validateAction);
        System.out.println("Result:" + response.getBody().isValid());
        return response.getBody();
    }

}
