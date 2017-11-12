package main.repositories;

import main.entities.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
    Card findByName (String name);
    Card findByCubeNumber (int number);
    void deleteByName (String name);
}
