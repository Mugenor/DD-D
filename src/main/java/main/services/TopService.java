package main.services;

import main.entities.Top;
import main.repositories.TopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TopService {
    private TopRepository topRepository;

    public TopService () {}

    @Autowired
    public TopService (TopRepository topRepository) {
        this.topRepository = topRepository;
    }

    /**
     * Find and return one Top by position from the database
     * @param position of required Top
     * @return one Top
     */
    public Top getByPosition(int position){
        return topRepository.findOne(position);
    }

    /**
     * Find and return one Top by username from the database
     * @param username of required Top
     * @return one Top
     */
    public Top getByUsername(String username){
        return topRepository.findByUserUsername(username);
    }

    /**
     * Return all Tops from the database
     * @return Tops
     */
    public Iterable<Top> getAllTops(){
        return topRepository.findAll();
    }

    /**
     * Save the Top if it's a new or update if it already exists
     * @param top which should be saved or updated
     * @return saved or updated Top
     */
    public Top saveOrUpdate(Top top){
        return topRepository.save(top);
    }

    /**
     * Delete the Top by position from the database
     * @param position of required Top
     */
    public void deleteByPosition(int position){
        topRepository.delete(position);
    }

    /**
     * Delete the Top by username from the database
     * @param username of required Top
     */
    public void deleteByUsername(String username){
        topRepository.deleteByUserUsername(username);
    }

    /**
     * Delete the required Top from the database
     * @param top which should be deleted
     */
    public void delete(Top top){
        topRepository.delete(top);
    }
}

