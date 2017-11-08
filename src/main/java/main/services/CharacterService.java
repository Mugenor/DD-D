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

    /**
     * Find and return one Character by id from the database
     * @param id of required Character
     * @return one Character
     */
    @Transactional
    public Character getById(long id){
        Character character = characterRepository.findOne(id);
        return character;
    }

    /**
     * Find and return one Character by name from the database
     * @param name of required Character
     * @return one Character
     */
    @Transactional
    public Character getByName(String name){
        Character character = characterRepository.findByName(name);
        return character;
    }

    /**
     * Return all Characters from the database
     * @return Characters
     */
    @Transactional
    public Iterable<Character> getAllCharacters(){
        Iterable<Character> characters = characterRepository.findAll();
        return characters;
    }

    /**
     * Save the Character if it's a new or update if it already exists
     * @param character which should be saved or updated
     * @return saved or updated Character
     */
    @Transactional
    public Character saveOrUpdate(Character character){
        return characterRepository.save(character);
    }

    /**
     * Delete the Character by id from the database
     * @param id of required Character
     */
    @Transactional
    public void deleteById(Long id){
        characterRepository.delete(id);
    }

    /**
     * Delete the Character by name from the database
     * @param name of the required Character
     */
    @Transactional
    public void deleteByName (String name) { characterRepository.deleteByName(name); }

    /**
     * Delete the required Character from the database
     * @param character which should be deleted
     */
    @Transactional
    public void delete(Character character){
        characterRepository.delete(character);
    }
}
