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
    public User findByStatus(int status);
    @Query("select friends from ourUser where username = :username")
    public List<User> getAllFriends(@Param("username") String username);
    @Query("select awaitingFriends from ourUser where username = :username")
    public List<User> getAllAwaitingFriends(@Param("username") String username);
}
