package main.services;

import main.entities.Person;
import main.repositories.PersonRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public Person findById(long id){
        Person person = personRepository.findOne(id);
        Hibernate.initialize(person.getFeatures());
        return person;
    }
}
