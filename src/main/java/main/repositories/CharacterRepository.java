package main.repositories;

import main.entities.Character;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Long> {
    public Character findByName(String name);
    public Character deleteByName (String name);
}
