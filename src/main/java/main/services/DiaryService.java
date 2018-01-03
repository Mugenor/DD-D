package main.services;

import main.entities.Diary;
import main.repositories.DiaryRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DiaryService {
    private DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(DiaryRepository diaryRepository){
        this.diaryRepository = diaryRepository;
    }
    public DiaryService(){}

    /**
     * Find and return one Diary by id from the database
     * @param id of required Diary
     * @return one Diary
     */
    public Diary getById(Integer id){
        Diary diary = diaryRepository.findOne(id);
        if(diary!=null) {
            Hibernate.initialize(diary.getAuthor());
            Hibernate.initialize(diary.getFinder());
            Hibernate.initialize(diary.getWhere());
            Hibernate.initialize(diary.getCreatures());
        }
        return diary;
    }

    /**
     * Return all Diaries without Creatures from the database
     * @return Diaries with minimum information
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
     * Save the Diary if it's a new or update if it already exists
     * @param diary which should be saved or updated
     * @return saved or updated Diary
     */
    public Diary saveOrUpdate(Diary diary){
        return diaryRepository.save(diary);
    }

    /**
     * Delete the Diary by id from the database
     * @param id of required Diary
     */
    public void deleteById(Integer id){
        diaryRepository.delete(id);
    }

    /**
     * Delete the required Diary from the database
     * @param diary which should be deleted
     */
    public void delete(Diary diary){
        diaryRepository.delete(diary);
    }
}
