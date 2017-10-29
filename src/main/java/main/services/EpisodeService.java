package main.services;

import main.entities.Episode;
import main.repositories.EpisodeRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EpisodeService {
    private EpisodeRepository episodeRepository;
    @Autowired
    public EpisodeService(EpisodeRepository episodeRepository){
        this.episodeRepository = episodeRepository;
    }
    public EpisodeService(){}

    /**
     * Find and return all episodes without Creature, Solver, Places, Participants from database
     * @return all episodes with minimum info
     */
    @Transactional
    public Iterable<Episode> getAllEpisodes(){
       return episodeRepository.findAll();
    }
    /**
     * Find and return Diary by id in database
     * @param series Series with this episode
     * @return Episode
     */
    @Transactional
    public Episode getBySeries(int series){
        Episode episode = episodeRepository.findBySeries(series);
            Hibernate.initialize(episode.getCreature());
            Hibernate.initialize(episode.getSolver());
            Hibernate.initialize(episode.getPlaces());
            Hibernate.initialize(episode.getParticipants());
        return episode;
    }

    /**
     * Find and return Diary by id in database
     * @param id of this episode
     * @return episode
     */
    @Transactional
    public Episode getById(Long id){
        Episode episode = episodeRepository.findOne(id);
        Hibernate.initialize(episode.getCreature());
        Hibernate.initialize(episode.getSolver());
        Hibernate.initialize(episode.getPlaces());
        Hibernate.initialize(episode.getParticipants());
        return episode;
    }

    /**
     * Save episode if it's new or update it if it's already exists in database
     * @param episode episode which should be updated or saved
     * @return Saved or updated episode
     */
    @Transactional
    public Episode saveOrUpdate(Episode episode){
        return episodeRepository.save(episode);
    }

    /**
     * Delete episode in series from database
     * @param series Series with this episode
     */
    @Transactional
    public void deleteBySeries(int series){
        episodeRepository.deleteBySeries(series);
    }

    /**
     * Delete episode by his id from database
     * @param id episode's id which should be deleted
     */
    @Transactional
    public void deleteById(Long id){
        episodeRepository.delete(id);
    }

    /**
     * Delete episode from database
     * @param episode episod which should be deleted
     */
    @Transactional
    public void delete(Episode episode){episodeRepository.delete(episode);}
}
