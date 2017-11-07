package main.services;

import main.entities.Card;
import main.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
    @Transactional
    public Card getById(long id){
        Card card = cardRepository.findOne(id);
        return card;
    }

    /**
     * Find and return one Card by name from the database
     * @param name of required Card
     * @return one Card
     */
    @Transactional
    public Card getByName(String name){
        Card card = cardRepository.findByName(name);
        return card;
    }

    /**
     * Find and return one Card by number of cube from the database
     * @param number of required Card
     * @return one Card
     */
    @Transactional
    public Card getByCubeNumber(int number){
        Card card = cardRepository.findByCubeNumber(number);
        return card;
    }

    /**
     * Return all Cards from the database
     * @return Cards
     */
    @Transactional
    public Iterable<Card> getAllCards(){
        Iterable<Card> cards = cardRepository.findAll();
        return cards;
    }

    /**
     * Save the Card if it's a new or update if it already exists
     * @param card which should be saved or updated
     * @return saved or updated Card
     */
    @Transactional
    public Card saveOrUpdate(Card card){
        return cardRepository.save(card);
    }

    /**
     * Delete the Card by id from the database
     * @param id of required Card
     */
    @Transactional
    public void deleteById(Long id){
        cardRepository.delete(id);
    }

    /**
     * Delete the Card by name from the database
     * @param name of the required Card
     */
    @Transactional
    public void deleteByName(String name){
        cardRepository.deleteByName(name);
    }

    /**
     * Delete the required Card from the database
     * @param card which should be deleted
     */
    @Transactional
    public void delete(Card card){
        cardRepository.delete(card);
    }
}
