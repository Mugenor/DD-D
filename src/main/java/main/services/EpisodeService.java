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

    @Transactional
    public Episode getBySeries(int series){
        Episode episode = episodeRepository.findBySeries(series);
            Hibernate.initialize(episode.getCreature());
            Hibernate.initialize(episode.getSeries());
            Hibernate.initialize(episode.getPlaces());
            Hibernate.initialize(episode.getParticipants());
        return episode;
    }
    @Transactional
    public Episode getById(Long id){
        Episode episode = episodeRepository.findOne(id);
        Hibernate.initialize(episode.getCreature());
        Hibernate.initialize(episode.getSeries());
        Hibernate.initialize(episode.getPlaces());
        Hibernate.initialize(episode.getParticipants());
        return episode;
    }
    @Transactional
    public Episode saveOrUpdate(Episode episode){
        return episodeRepository.save(episode);
    }
    @Transactional
    public void deleteBySeries(int series){
        episodeRepository.deleteBySeries(series);
    }
    @Transactional
    public void deleteById(Long id){
        episodeRepository.delete(id);
    }
}
