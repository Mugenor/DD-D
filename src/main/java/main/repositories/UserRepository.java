package main.repositories;

import main.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String>{
    User findByUsername(String username);

    List<User> findByStatus(int status);

    void deleteByUsername (String username);

    List<User> findFriendsByUsername(String username);

    List<User> findAwaitingFriendsByUsername(String username);
}
