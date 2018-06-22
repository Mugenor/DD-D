package main.repositories;

import main.entities.Top;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopRepository extends CrudRepository<Top, Integer> {
    Top findByUserUsername( String username);
    void deleteByUserUsername(String username);
}
