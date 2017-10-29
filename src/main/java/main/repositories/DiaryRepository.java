package main.repositories;

import main.entities.Creature;
import main.entities.Diary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository  extends CrudRepository<Diary, Integer>{
    @Query ("select d.creatures from diary d where d.id=:id")
    public List<Creature> findCreaturesByDiaryId(@Param("id") Integer id);
}
