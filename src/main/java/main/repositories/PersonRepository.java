package main.repositories;

import main.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    public Person findByName(String name);
    public void deleteByName(String name);
}
