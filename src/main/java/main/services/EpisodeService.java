package main.services;

import main.entities.Episode;
import main.repositories.EpisodeRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EpisodeService {
    private EpisodeRepository episodeRepository;
    @Autowired
    public EpisodeService(EpisodeRepository episodeRepository){
        this.episodeRepository = episodeRepository;
    }
    public EpisodeService(){}

    /**
     * Find and return one Episode by series from the database
     * @param series of required Episode
     * @return one Episode
     */
    public Episode getBySeries(int series){
        Episode episode = episodeRepository.findBySeries(series);
        initializeEpisode(episode);
        return episode;
    }

    /**
     * Find and return one Episode by id from the database
     * @param id of required Episode
     * @return one Episode
     */
    public Episode getById(Long id){
        Episode episode = episodeRepository.findOne(id);
        initializeEpisode(episode);
        return episode;
    }

    /**
     * Return all Episodes without Creatures, Solvers, Places and Participants from the database
     * @return Episodes with minimum information
     */
    public Iterable<Episode> getAllEpisodes(){
        return episodeRepository.findAll();
    }

    /**
     * Save the Episode if it's a new or update if it already exists
     * @param episode which should be saved or updated
     * @return saved or updated Episode
     */
    public Episode saveOrUpdate(Episode episode){
        return episodeRepository.save(episode);
    }

    /**
     * Delete the Episode by series from the database
     * @param series of required Episode
     */
    public void deleteBySeries(int series){
        episodeRepository.deleteBySeries(series);
    }

    /**
     * Delete the Episode by id from the database
     * @param id of required Episode
     */
    public void deleteById(Long id){
        episodeRepository.delete(id);
    }

    /**
     * Delete the required Episode from the database
     * @param episode which should be deleted
     */
    public void delete(Episode episode){episodeRepository.delete(episode);}

    private void initializeEpisode(Episode episode){
        if(episode!=null) {
            Hibernate.initialize(episode.getCreature());
            Hibernate.initialize(episode.getSolver());
            Hibernate.initialize(episode.getPlaces());
            Hibernate.initialize(episode.getParticipants());
        }
    }
}
