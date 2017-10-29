package myGroup;

import main.entities.Feature;
import main.entities.Person;
import main.entities.Place;
import main.entities.Position;
import main.repositories.FeatureRepository;
import main.repositories.PersonRepository;
import main.repositories.PlaceRepository;
import main.repositories.PositionRepository;
import main.services.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PersonService.class, PersonRepository.class})
@DataJpaTest
//@ComponentScan(basePackages = {"main.entities", "main.repositories", "main.services"})
public class PersonRepositoryTest {
    @Autowired
    private PersonService personService;
    private static final Logger log = LoggerFactory.getLogger(PersonRepositoryTest.class);

    @Test
    public void testPersonRepository(){
        Person person = personService.getById(1L);

    }

}
