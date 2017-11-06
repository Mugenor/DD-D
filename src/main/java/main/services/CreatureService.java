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

    @Transactional
    public Creature getById(long id){
        Creature creature = creatureRepository.findOne(id);
        Hibernate.initialize(creature.getDiary());
        Hibernate.initialize(creature.getEpisode());
        return creature;
    }

    @Transactional
    public Creature getByName(String name){
        Creature creature = creatureRepository.findByName(name);
        Hibernate.initialize(creature.getDiary());
        Hibernate.initialize(creature.getEpisode());
        return creature;
    }

    @Transactional
    public List<Creature> getByIdDiary(int id) {
        List<Creature> creatures = diaryRepository.findCreaturesByDiaryId(id);
        for(Creature creature: creatures){
            Hibernate.initialize(creature.getDiary());
            Hibernate.initialize(creature.getEpisode());
        }
        return creatures;
    }

    @Transactional
    public Iterable<Creature> getAllCreatures(){
        Iterable<Creature> creatures = creatureRepository.findAll();
        for(Creature creature: creatures){
            Hibernate.initialize(creature.getDiary());
            Hibernate.initialize(creature.getEpisode());
        }
        return creatures;
    }



    @Transactional
    public Creature saveOrUpdate(Creature creature){
        return creatureRepository.save(creature);
    }
    @Transactional
    public void deleteById(Long id){
        creatureRepository.delete(id);
    }
    @Transactional
    public void delete(Creature creature){
        creatureRepository.delete(creature);
    }
}
