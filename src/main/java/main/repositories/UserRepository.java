package main.repositories;

import main.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
    public User findByUsername(String username);
    @Query("select ou from ourUser ou where ou.status = :status")
    public List<User> findByStatus(@Param("status") int status);
    public void deleteByUsername (String username);
    @Query("select friends from ourUser where username = :username")
    public List<User> getAllFriends(@Param("username") String username);
    @Query("select awaitingFriends from ourUser where username = :username")
    public List<User> getAllAwaitingFriends(@Param("username") String username);
}
