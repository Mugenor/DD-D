package main.repositories;

import main.entities.AlmostUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AlmostUserRepository extends CrudRepository<AlmostUser, String> {
    List<AlmostUser> findAllByUsernameOrMail(String username, String mail);

    AlmostUser findByHashValue(String hashValue);

    void deleteAllByDateBefore(Date date);
}