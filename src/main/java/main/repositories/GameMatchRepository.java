package main.repositories;

import main.entities.GameMatch;
import main.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameMatchRepository extends CrudRepository<GameMatch, Integer> {
    @Query("select gm from GameMatch gm where gm.player1=:user or gm.player2=:user")
    List<GameMatch> findAllByUser(@Param("user") User user);
}
