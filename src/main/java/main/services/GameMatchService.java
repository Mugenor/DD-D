package main.services;

import main.entities.GameMatch;
import main.entities.User;
import main.repositories.GameMatchRepository;
import main.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameMatchService {
    private GameMatchRepository gameMatchRepository;
    private UserRepository userRepository;

    public GameMatchService() {}

    @Autowired
    public GameMatchService(GameMatchRepository gameMatchRepository, UserRepository userRepository) {
        this.gameMatchRepository = gameMatchRepository;
        this.userRepository = userRepository;
    }

    public List<GameMatch> getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return gameMatchRepository.findAllByUser(user);
    }

    public GameMatch saveGameMatch(String username1, String username2){
        User user1 = userRepository.findByUsername(username1);
        User user2 = userRepository.findByUsername(username2);
        return gameMatchRepository.save(new GameMatch(user1, user2));
    }
}
