package main;

import main.entities.*;
import main.entities.Character;
import main.services.CharacterService;
import main.services.FeedbackService;
import main.services.PersonService;
import org.hibernate.Hibernate;
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
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonService.class)
@Configuration
@EnableAutoConfiguration
@ComponentScan("main.services")
public class PersonRepositoryTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private CharacterService characterService;
    private static final Logger log = LoggerFactory.getLogger(PersonRepositoryTest.class);

    @Test
    public void testPersonRepository(){
        // Saving first person
        List<Feature> features = new ArrayList<>();
        features.add(new Feature("feature1"));
        features.add(new Feature("feature2"));
        Place place = new Place("cafe");
        Position position = new Position("worker", place);
        Person person = new Person("Ilya Chernov", Person.MALE, 18, "pictures/Ilya.png", features, position);
        person = personService.saveOrUpdate(person);
        System.out.println(person);
        Skill skill = new Skill("some skill", "skill", "pictures/skill.png", 5);
        Character character = new Character("Ilya", 9000, "blablabla", skill, person);
        System.out.println("Character: " + character);
        characterService.saveOrUpdate(character);
        System.out.println("Person: " + person);
        character = characterService.getByName("Ilya");
        System.out.println(character);
        System.out.println("characterService.getByName(\"Ilya\"): " + characterService.getByName("Ilya"));
        System.out.println("--------------------------------------------------------------------------");
        // Saving second person
        features.clear();
        features.add(new Feature("feature3"));
        features.add(new Feature("feature4"));
        place = new Place("anotherCafe");
        position = new Position("anotherWorker", place);
        person = new Person("Liza Klokova", Person.FEMALE, 18, "pictures/Liza.png", features, position);
        skill = new Skill("anotherSkill", "skill Lizi", "pictures/anotherSkill.png", 5);
        character = new Character("Liza", 8999, "blabla", skill, person);
        System.out.println("Before saving = " + character);
        personService.saveOrUpdate(person);
        characterService.saveOrUpdate(character);
        System.out.println("Liza's character: " + character);
        // Updating second person
        person.setName("Lizochka Klokova");
        personService.saveOrUpdate(person);
        // Get all characters
        showAllPersons();
        // Deleting first character
        characterService.deleteByName("Ilya");
        showAllPersons();
        // Deleting second person
        personService.deleteByName("Lizochka Klokova");
        showAllPersons();

        character = characterService.getByName("Liza");
        System.out.println("Liza: " + character);
        Assert.assertTrue(!Hibernate.isInitialized(character.getPerson().getFeatures())
                && !Hibernate.isInitialized(character.getPerson().getPosition()));
    }
    private void showAllPersons(){
        Iterable<Character> characters = characterService.getAllCharacters();
        System.out.println("Все персонажи");
        for(Character charact: characters){
            System.out.println(charact);
        }
        System.out.println("Конец");
    }
}
