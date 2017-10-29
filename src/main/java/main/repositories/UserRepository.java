package main.repositories;

import main.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public interface UserRepository extends CrudRepository<User, String>{
    public User findByUsername(String username);
    @Query("select u.friends from ourUser u where u.username = :username")
    public List<User> getAllFriends(@Param("username") String username);
    @Query("select u.awaitingFriends from ourUser u where u.username = :username")
    public List<User> getAllAwaitingFriends(@Param("username") String username);
}
