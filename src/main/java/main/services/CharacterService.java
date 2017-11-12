package main.services;

import main.entities.Character;
import main.repositories.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
    public Character getById(long id){
        return characterRepository.findOne(id);
    }

    /**
     * Find and return one Character by name from the database
     * @param name of required Character
     * @return one Character
     */
    public Character getByName(String name){
        return characterRepository.findByName(name);
    }

    /**
     * Return all Characters from the database
     * @return Characters
     */
    public Iterable<Character> getAllCharacters(){
        return characterRepository.findAll();
    }

    /**
     * Save the Character if it's a new or update if it already exists
     * @param character which should be saved or updated
     * @return saved or updated Character
     */
    public Character saveOrUpdate(Character character){
        return characterRepository.save(character);
    }

    /**
     * Delete the Character by id from the database
     * @param id of required Character
     */
    public void deleteById(Long id){
        characterRepository.delete(id);
    }

    /**
     * Delete the Character by name from the database
     * @param name of the required Character
     */
    public void deleteByName (String name) { characterRepository.deleteByName(name); }

    /**
     * Delete the required Character from the database
     * @param character which should be deleted
     */
    public void delete(Character character){
        characterRepository.delete(character);
    }
}
