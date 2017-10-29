package main.repositories;

import main.entities.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
    public Card findByName (String name);
    public Card findByCubeNumber (int number);
}
