package main.repositories;

import main.entities.Character;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Integer> {
    Character findByName(String name);

    void deleteByName (String name);
}
