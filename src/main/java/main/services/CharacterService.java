package main.services;

import main.entities.Character;
import main.repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterService {
    private CharacterRepository characterRepository;

    public CharacterService () {}

    @Autowired
    public CharacterService (CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Transactional
    public Character getById(long id){
        Character character = characterRepository.findOne(id);
        return character;
    }

    @Transactional
    public Character getByName(String name){
        Character character = characterRepository.findByName(name);
        return character;
    }

    @Transactional
    public Iterable<Character> getAllCreatures(){
        Iterable<Character> characters = characterRepository.findAll();
        return characters;
    }

    @Transactional
    public Character saveOrUpdate(Character character){
        return characterRepository.save(character);
    }
    @Transactional
    public void deleteById(Long id){
        characterRepository.delete(id);
    }
    @Transactional
    public void delete(Character character){
        characterRepository.delete(character);
    }
}
