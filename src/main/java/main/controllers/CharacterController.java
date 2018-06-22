package main.controllers;

import main.entities.Character;
import main.services.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/character", produces = MediaType.APPLICATION_JSON_VALUE)
public class CharacterController {
    private CharacterService characterService;

    public CharacterController() {}

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Character> getAllCharacters(){
        return characterService.getAllCharacters();
    }

    @RequestMapping(path = "/id/{id}", method = RequestMethod.GET)
    public Character getCharacterById(@PathVariable Integer id){
        return characterService.getById(id);
    }

    @RequestMapping(path = "/name/{name}", method = RequestMethod.GET)
    public Character getCharacterByName(@PathVariable String name){
        return characterService.getByName(name);
    }
}
