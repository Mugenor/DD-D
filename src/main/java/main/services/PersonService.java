package main.services;

import main.entities.Person;
import main.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PersonService {
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    public PersonService(){}

    /**
     * Find and return Person by id in database
     * @param id person's id
     * @return person with id = id
     */
    @Transactional
    public Person getById(long id){
        Person person = personRepository.findOne(id);
        Hibernate.initialize(person.getFeatures());
        Hibernate.initialize(person.getPosition());
        return person;
    }

    /**
     * Find and return Person by his name in database
     * @param name person's name
     * @return person with name = name
     */
    @Transactional
    public Person getByName(String name){
        Person person = personRepository.findByName(name);
            Hibernate.initialize(person.getFeatures());
            Hibernate.initialize(person.getPosition());
        return person;
    }

    /**
     * Find and return all persons in database
     * @return iterable array of persons
     */
    @Transactional
    public Iterable<Person> getAllPersons(){
        Iterable<Person> people = personRepository.findAll();
        for(Person person: people){
            Hibernate.initialize(person.getPosition());
            Hibernate.initialize(person.getFeatures());
        }
        return people;
    }

    /**
     *
     * Save person if it's new or update if it's already exists in database
     * @param person person
     * @return person which was saved or updated
     */
    @Transactional
    public Person saveOrUpdate(Person person){
        return personRepository.save(person);
    }

    /**
     * Delete person by his id from database
     * @param id person's id
     */
    @Transactional
    public void deleteById(Long id){
        personRepository.delete(id);
    }

    /**
     * Delete person from database
     * @param person person
     */
    @Transactional
    public void delete(Person person){
        personRepository.delete(person);
    }

    /**
     * Delete person by his name from database
     * @param name person's name
     */
    @Transactional void deleteByName(String name){
        personRepository.deleteByName(name);
    }
}
