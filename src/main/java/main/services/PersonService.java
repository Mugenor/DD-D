package main.services;

import main.entities.Person;
import main.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public Person findById(long id){
        Person person = personRepository.findOne(id);
        Hibernate.initialize(person.getFeatures());
        Hibernate.initialize(person.getPosition());
        return person;
    }

    @Transactional
    public List<Person> findByName(String name){
        List<Person> persons = personRepository.findByName(name);
        for(Person person: persons){
            Hibernate.initialize(person.getFeatures());
            Hibernate.initialize(person.getPosition());
        }
        return persons;
    }

    public Iterable<Person> getAllPersons(){
        Iterable<Person> people = personRepository.findAll();
        for(Person person: people){
            Hibernate.initialize(person.getPosition());
            Hibernate.initialize(person.getFeatures());
        }
        return people;
    }
}
