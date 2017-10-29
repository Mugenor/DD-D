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
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    public PersonService(){}

    @Transactional
    public Person getById(long id){
        Person person = personRepository.findOne(id);
        Hibernate.initialize(person.getFeatures());
        Hibernate.initialize(person.getPosition());
        return person;
    }

    @Transactional
    public Person getByName(String name){
        Person person = personRepository.findByName(name);
            Hibernate.initialize(person.getFeatures());
            Hibernate.initialize(person.getPosition());
        return person;
    }
    @Transactional
    public Iterable<Person> getAllPersons(){
        Iterable<Person> people = personRepository.findAll();
        for(Person person: people){
            Hibernate.initialize(person.getPosition());
            Hibernate.initialize(person.getFeatures());
        }
        return people;
    }
    @Transactional
    public Person saveOrUpdate(Person person){
        return personRepository.save(person);
    }
    @Transactional
    public void deleteById(Long id){
        personRepository.delete(id);
    }
    @Transactional
    public void delete(Person person){
        personRepository.delete(person);
    }
}
