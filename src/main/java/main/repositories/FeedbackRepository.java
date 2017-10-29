package main.repositories;

import main.entities.Feedback;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

    @Query("select f FROM feedback f where f.user.username = :username")
    public List<Feedback> findByUser_Username(@Param("username") String username);
    public List<Feedback> findByResponseIsNull();
    public void deleteAllByDateBefore(Date date);
}
