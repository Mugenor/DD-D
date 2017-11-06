package main.services;

import main.entities.Top;
import main.repositories.TopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TopService {
    private TopRepository topRepository;

    public TopService () {}

    @Autowired
    public TopService (TopRepository topRepository) {
        this.topRepository = topRepository;
    }

    @Transactional
    public Top getByPosition(int position){
        Top top = topRepository.findOne(position);
        return top;
    }

    @Transactional
    public Top getByUsername(String name){
        Top top = topRepository.findByUsername(name);
        return top;
    }

    @Transactional
    public Iterable<Top> getAllTops(){
        Iterable<Top> tops = topRepository.findAll();
        return tops;
    }

    @Transactional
    public Top saveOrUpdate(Top top){
        return topRepository.save(top);
    }
    @Transactional
    public void deleteByPosition(int position){
        topRepository.delete(position);
    }
    @Transactional
    public void delete(Top top){
        topRepository.delete(top);
    }
}

