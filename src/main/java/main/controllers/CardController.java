package main.controllers;

import main.entities.Card;
import main.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/card", produces = MediaType.APPLICATION_JSON_VALUE)
public class CardController {
    private CardService cardService;

    public CardController(){}
    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Card> getAllCards(){
        return cardService.getAllCards();
    }

    @RequestMapping(path = "/name/{name}", method = RequestMethod.GET)
    public Card getCardByName(@PathVariable String name){
        return cardService.getByName(name);
    }

    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    public Card getCardById(@PathVariable Integer id){
        return cardService.getById(id);
    }

    @RequestMapping(path = "/cube-number/{number}", method = RequestMethod.GET)
    public Card getCardsByCubeNumber(@PathVariable Integer number){
        return cardService.getByCubeNumber(number);
    }
}
