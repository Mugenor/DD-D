package main.repositories;

import main.entities.Top;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TopRepository extends CrudRepository<Top, Integer> {
    @Query ("select top from top where user.username=:username")
    public Top findByUsername (@Param("username") String username);
}
