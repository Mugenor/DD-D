package main.repositories;

import main.entities.Character;
import main.entities.Person;
import main.entities.Skill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Long> {
    public Character findByName(String name);
}
