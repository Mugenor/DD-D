package main;

import main.security.util.OpenAMRestConsumer;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;


public class OpenAMRestConsumerTest {
    private static final Logger logger = Logger.getLogger(OpenAMRestConsumerTest.class);

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
}
