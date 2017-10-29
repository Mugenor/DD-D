package main.services;


import main.entities.Diary;
import main.repositories.DiaryRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiaryService {
    private DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(DiaryRepository diaryRepository){
        this.diaryRepository = diaryRepository;
    }
    public DiaryService(){}

    @Transactional
    public Diary getById(Integer id){
        Diary diary = diaryRepository.findOne(id);
        Hibernate.initialize(diary.getAuthor());
        Hibernate.initialize(diary.getFinder());
        Hibernate.initialize(diary.getWhere());
        Hibernate.initialize(diary.getCreatures());
        return diary;
    }
    @Transactional
    public Diary saveOrUpdate(Diary diary){
        return diaryRepository.save(diary);
    }
    @Transactional
    public void delete(Integer id){
        diaryRepository.delete(id);
    }
}
