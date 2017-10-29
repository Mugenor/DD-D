package main.repositories;

import main.entities.Card;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card, Long> {
    public Card findByName (String name);
    public Card findByCubeNumber (int number);
}
