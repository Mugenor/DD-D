package main;

import main.entities.Feature;
import main.entities.Person;
import main.entities.Place;
import main.entities.Position;
import main.repositories.*;
import main.services.PersonService;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
    }

    @Bean
    public CommandLineRunner demo(PersonService personService){
        return (args)-> {
            Person person = new Person();
            Position position = new Position();
            Place place = new Place();
            place.setPlace("Shop");
            position.setName("Assistent");
            position.setPlace(place);
            person.setAge(10);
            person.setName("Ilya");
            person.setPicture("ilusha.png");
            person.setSex(Person.MALE);
            person.setPosition(position);
            Collection<Feature> features = new ArrayList<>(2);
            features.add(new Feature("nice boy"));
            features.add(new Feature("smart"));
            person.setFeatures(features);
            personService.saveOrUpdate(person);
            person = personService.getById(1L);
            if(person!=null) {
                log.info("Person:\n " + person.toString());
            } else {
                log.info("Peson: null");
            }
            person = personService.getByName("Ilya");
            if(person!=null) {
                log.info("Person:\n " + person.toString());
            } else {
                log.info("Peson: null");
            }
        };
    }
}
