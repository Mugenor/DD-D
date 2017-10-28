package main.repositories;

import main.entities.Diary;
import org.springframework.data.repository.CrudRepository;

public interface DiaryRepository  extends CrudRepository<Diary, Integer>{
}
