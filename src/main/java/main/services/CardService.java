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

    @Transactional
    public Card getById(long id){
        Card card = cardRepository.findOne(id);
        return card;
    }

    @Transactional
    public Card getByName(String name){
        Card card = cardRepository.findByName(name);
        return card;
    }

    @Transactional
    public Card getByCubeNumber(int number){
        Card card = cardRepository.findByCubeNumber(number);
        return card;
    }

    @Transactional
    public Iterable<Card> getAllCards(){
        Iterable<Card> cards = cardRepository.findAll();
        return cards;
    }

    @Transactional
    public Card saveOrUpdate(Card card){
        return cardRepository.save(card);
    }
    @Transactional
    public void deleteById(Long id){
        cardRepository.delete(id);
    }
    @Transactional
    public void delete(Card card){
        cardRepository.delete(card);
    }
}
