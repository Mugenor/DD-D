package main;

import main.services.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonService.class)
@Configuration
@EnableAutoConfiguration
@ComponentScan("main.services")
public class PersonRepositoryTest {
    @Autowired
    private PersonService personService;
    private static final Logger log = LoggerFactory.getLogger(PersonRepositoryTest.class);

    @Test
    public void testPersonRepository(){
    }

}
