package main.services;

import main.entities.Card;
import main.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CardService {
    private CardRepository cardRepository;

    public CardService () {}

    @Autowired
    public CardService (CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    /**
     * Find and return one Card by id from the database
     * @param id of required Card
     * @return one Card
     */
    public Card getById(int id){
        return cardRepository.findOne(id);
    }

    /**
     * Find and return one Card by name from the database
     * @param name of required Card
     * @return one Card
     */
    public Card getByName(String name){
        return cardRepository.findByName(name);
    }

    /**
     * Find and return one Card by number of cube from the database
     * @param number of required Card
     * @return one Card
     */
    public Card getByCubeNumber(int number){
        return cardRepository.findByCubeNumber(number);
    }

    /**
     * Return all Cards from the database
     * @return Cards
     */
    public Iterable<Card> getAllCards(){
        return cardRepository.findAll();
    }

    /**
     * Save the Card if it's a new or update if it already exists
     * @param card which should be saved or updated
     * @return saved or updated Card
     */
    public Card saveOrUpdate(Card card){
        return cardRepository.save(card);
    }

    /**
     * Delete the Card by id from the database
     * @param id of required Card
     */
    public void deleteById(Integer id){
        cardRepository.delete(id);
    }

    /**
     * Delete the Card by name from the database
     * @param name of the required Card
     */
    public void deleteByName(String name){
        cardRepository.deleteByName(name);
    }

    /**
     * Delete the required Card from the database
     * @param card which should be deleted
     */
    public void delete(Card card){
        cardRepository.delete(card);
    }
}
