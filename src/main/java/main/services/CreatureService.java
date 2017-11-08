package main.services;

import main.entities.Creature;
import main.repositories.CreatureRepository;
import main.repositories.DiaryRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CreatureService {
    private CreatureRepository creatureRepository;
    private DiaryRepository diaryRepository;

    public CreatureService() {}

    @Autowired
    public CreatureService (CreatureRepository creatureRepository, DiaryRepository diaryRepository) {
        this.creatureRepository = creatureRepository;
        this.diaryRepository = diaryRepository;
    }

    /**
     * Find and return one Creature by id from the database
     * @param id of required Creature
     * @return one Creature
     */
    @Transactional
    public Creature getById(long id){
        Creature creature = creatureRepository.findOne(id);
        Hibernate.initialize(creature.getDiary());
        Hibernate.initialize(creature.getEpisode());
        return creature;
    }

    /**
     * Find and return one Creature by name from the database
     * @param name of required Creature
     * @return one Creature
     */
    @Transactional
    public Creature getByName(String name){
        Creature creature = creatureRepository.findByName(name);
        Hibernate.initialize(creature.getDiary());
        Hibernate.initialize(creature.getEpisode());
        return creature;
    }

    /**
     * Find and return the list of Creatures by id of Diary from the database
     * @param id of required Diary
     * @return list of Creatures
     */
    @Transactional
    public List<Creature> getByIdDiary(int id) {
        List<Creature> creatures = diaryRepository.findCreaturesByDiaryId(id);
        for(Creature creature: creatures){
            Hibernate.initialize(creature.getDiary());
            Hibernate.initialize(creature.getEpisode());
        }
        return creatures;
    }

    /**
     * Return all Creatures without Diaries and Episodes from the database
     * @return Creatures with minimum information
     */
    @Transactional
    public Iterable<Creature> getAllCreatures(){
        Iterable<Creature> creatures = creatureRepository.findAll();
        for(Creature creature: creatures){
            Hibernate.initialize(creature.getDiary());
            Hibernate.initialize(creature.getEpisode());
        }
        return creatures;
    }

    /**
     * Save the Creature if it's a new or update if it already exists
     * @param creature which should be saved or updated
     * @return saved or updated Creature
     */
    @Transactional
    public Creature saveOrUpdate(Creature creature){
        return creatureRepository.save(creature);
    }

    /**
     * Delete the Creature by id from the database
     * @param id of required Creature
     */
    @Transactional
    public void deleteById(Long id){
        creatureRepository.delete(id);
    }

    /**
     * Delete the required Creature from the database
     * @param creature which should be deleted
     */
    @Transactional
    public void delete(Creature creature){
        creatureRepository.delete(creature);
    }
}
