package main.repositories;

import main.entities.Creature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatureRepository  extends CrudRepository<Creature, Integer>{
    Creature findByName(String name);
}
