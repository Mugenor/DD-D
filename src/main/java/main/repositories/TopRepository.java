package main.repositories;

import main.entities.Top;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopRepository extends CrudRepository<Top, Integer> {
    @Query ("select t from top t where t.user.username=:username")
    public Top findByUsername (@Param("username") String username);
    @Query ("delete from top where user.username=:username")
    public void deleteByUsername (@Param("username") String username);
}
