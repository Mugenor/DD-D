package main.repositories;

import main.entities.Creature;
import org.springframework.data.repository.CrudRepository;

public interface CreatureRepository  extends CrudRepository<Creature, Long>{
}
