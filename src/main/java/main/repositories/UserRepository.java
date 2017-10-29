package main.repositories;

import main.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>{
    public User findByUsername(String username);
}
