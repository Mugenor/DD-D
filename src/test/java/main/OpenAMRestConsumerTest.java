package main;

import com.google.gson.Gson;
import main.entities.AlmostUser;
import main.repositories.AlmostUserRepository;
import main.security.util.OpenAMRestConsumer;
import main.security.util.entities.LogoutResponse;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class OpenAMRestConsumerTest {
    private static final Logger logger = Logger.getLogger(OpenAMRestConsumerTest.class);
    @Autowired
    private AlmostUserRepository almostUserRepository;

    @Test
    public void loginTest(){
        OpenAMRestConsumer consumer = new OpenAMRestConsumer("http://openam.mydomain.com:8081/openam");
        String tokenId = consumer.loginUser("Ilya", "qwerty");
        logger.info("Successful response: " + tokenId);
        try {
            logger.info("Unsuccessful response: " + consumer.loginUser("Mugenor", "password"));
        } catch (HttpClientErrorException e){
            logger.info("Error response: " + e.getMessage());
        }

        logger.info("Session validation: " + consumer.validateSessionByToken(tokenId));
        logger.info("Session validation: " + consumer.validateSessionByToken(tokenId));

        logger.info("Logged out: " + consumer.logoutUserByToken(tokenId));
        try {
            logger.info(consumer.logoutUserByToken(tokenId));
        } catch (HttpClientErrorException e){
            logger.info("Error response: " + e.getMessage());
        }
        logger.info("Session validation: " + consumer.validateSessionByToken(tokenId));
    }

    @Test
    public void test()throws NoSuchAlgorithmException{
        AlmostUser almostUser = new AlmostUser();
        almostUser.setPassword("qwerty");
        almostUser.setHashValue("hash");
        almostUser.setMail("mail");
        almostUser.setDate(new Date());
        almostUserRepository.save(almostUser);
    }
}
