package main;

import main.entities.Feature;
import main.entities.Person;
import main.entities.Place;
import main.entities.Position;
import main.repositories.*;
import main.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
    }

    @Bean
    public CommandLineRunner demo(PersonService personService, PersonRepository personRepository, FeatureRepository featureRepository, PlaceRepository placeRepository
                    , PositionRepository positionRepository){
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
            personRepository.save(person);
            person = personService.findById(1L);
            if(person!=null) {
                log.info("Person:\n " + person.toString());
            } else {
                log.info("Peson: null");
            }
            Iterable<Feature> newFeatures = featureRepository.findAll();
            for(Feature feature: newFeatures){
                log.info("Feature: " + feature.toString());
            }
            Iterable<Place> places = placeRepository.findAll();
            for(Place newPlace: places){
                log.info("Place: " + newPlace.toString());
            }
            Iterable<Position> positions = positionRepository.findAll();
            for(Position position1: positions){
                log.info("Position: " + position1.toString());
            }
        };
    }
}
