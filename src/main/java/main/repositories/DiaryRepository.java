package main.repositories;

import main.entities.Creature;
import main.entities.Diary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository  extends CrudRepository<Diary, Integer>{
    List<Creature> findCreaturesById(Integer id);
}
