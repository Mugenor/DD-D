package main.repositories;

import main.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, java.lang.String>{
    User findByUsername(String username);

    Set<User> findAllByUsernameContainingIgnoreCase(String username);

    void deleteByUsername (String username);

    List<User> findFriendsByUsername(String username);

    List<User> findAwaitingFriendsByUsername(String username);

    List<User> findAllByUsernameOrMail(String username, String mail);

    List<String> findUsernameByStatus(String status);
}
