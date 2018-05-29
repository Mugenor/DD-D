package main.repositories;

import main.entities.News;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface NewsRepository extends CrudRepository<News, Integer> {
    Iterable<News> getAllByTopic(String topic);
    Iterable<News> getAllByDateAfter(Date after);
    void deleteAllByTopic(String topic);
    void deleteAllByDateBefore(Date before);
}
