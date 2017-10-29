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

    /**
     * Find and return all diaries without Creatures from database
     * @return diaries with minimum info
     */
    public Iterable<Diary> getAllDiaries(){
        Iterable<Diary> diaries = diaryRepository.findAll();
        for(Diary diary: diaries){
            Hibernate.initialize(diary.getWhere());
            Hibernate.initialize(diary.getFinder());
            Hibernate.initialize(diary.getAuthor());
        }
        return diaries;
    }
    /**
     * Find and return Diary by id in database
     * @param id Diary id in database
     * @return Diary with id = @param id
     */
    @Transactional
    public Diary getById(Integer id){
        Diary diary = diaryRepository.findOne(id);
        Hibernate.initialize(diary.getAuthor());
        Hibernate.initialize(diary.getFinder());
        Hibernate.initialize(diary.getWhere());
        Hibernate.initialize(diary.getCreatures());
        return diary;
    }

    /**
     * Save diary if it's new or update if it's already exists in database
     * @param diary Diary
     * @return Diary which was saved
     */
    @Transactional
    public Diary saveOrUpdate(Diary diary){
        return diaryRepository.save(diary);
    }

    /**
     * Delete diary by id from database
     * @param id of diary which should be deleted
     */
    @Transactional
    public void delete(Integer id){
        diaryRepository.delete(id);
    }

    /**
     * Delete diary from database
     * @param diary which should be deleted
     */
    @Transactional
    public void delete(Diary diary){
        diaryRepository.delete(diary);
    }
}
