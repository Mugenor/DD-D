package main.services;

import main.entities.Person;
import main.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    public PersonService(){}

    /**
     * Find and return one Person by id from the database
     * @param id of required Person
     * @return one Person
     */
    public Person getById(long id){
        Person person = personRepository.findOne(id);
        Hibernate.initialize(person.getFeatures());
        Hibernate.initialize(person.getPosition());
        return person;
    }

    /**
     * Find and return one Person by name from the database
     * @param name of required Person
     * @return one Person
     */
    public Person getByName(String name){
        Person person = personRepository.findByName(name);
            Hibernate.initialize(person.getFeatures());
            Hibernate.initialize(person.getPosition());
        return person;
    }

    /**
     * Return all Persons without Features and Positions from the database
     * @return Persons with minimum information
     */
    public Iterable<Person> getAllPersons(){
        Iterable<Person> people = personRepository.findAll();
        for(Person person: people){
            Hibernate.initialize(person.getPosition());
            Hibernate.initialize(person.getFeatures());
        }
        return people;
    }

    /**
     * Save the Person if it's a new or update if it already exists
     * @param person which should be saved or updated
     * @return saved or updated Person
     */
    public Person saveOrUpdate(Person person){
        return personRepository.save(person);
    }

    /**
     * Delete the Person by id from the database
     * @param id of required Person
     */
    public void deleteById(Long id){
        personRepository.delete(id);
    }

    /**
     * Delete the required Person from the database
     * @param person which should be deleted
     */
    public void delete(Person person){
        personRepository.delete(person);
    }

    /**
     * Delete the Person by name from the database
     * @param name of required Person
     */
    public void deleteByName(String name){
        personRepository.deleteByName(name);
    }
}
