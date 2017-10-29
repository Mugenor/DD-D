package main.repositories;

import main.entities.Diary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository  extends CrudRepository<Diary, Integer>{
}
