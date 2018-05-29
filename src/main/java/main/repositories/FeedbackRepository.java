package main.repositories;

import main.entities.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Integer> {

    List<Feedback> findByUserUsername(@Param("username") String username);
    List<Feedback> findByResponseIsNull();
    void deleteAllByDateBefore(Date date);
}
