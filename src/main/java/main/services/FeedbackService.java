package main.services;

import main.entities.Feedback;
import main.repositories.FeedbackRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    public FeedbackService(){}

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository){
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Find and return all feedbacks in database
     * @return all feedbacks
     */
    public Iterable<Feedback> getAllFeebacks(){
        Iterable<Feedback> feedbacks = feedbackRepository.findAll();
        for(Feedback feedback: feedbacks){
            Hibernate.initialize(feedback.getUser());
        }
        return feedbacks;
    }
    /**
     * Find and return Feedback by id in database
     * @param id feedback's id which should be returned
     * @return Feedback
     */
    @Transactional
    public Feedback getById(Long id){
        Feedback feedback = feedbackRepository.findOne(id);
        Hibernate.initialize(feedback.getUser());
        return feedback;
    }

    /**
     * Find and return all Feedbacks of User in database
     * @param username User's username
     * @return List of Feedbacks from this user
     */
    @Transactional
    public List<Feedback> getByUserName(String username){
        List<Feedback> feedbacks = feedbackRepository.findByUser_Username(username);
        for(Feedback feedback: feedbacks){
            Hibernate.initialize(feedback.getUser());
        }
        return feedbacks;
    }

    /**
     * Find and return all unanswered Feedbacks in database
     * @return unaswered Feedbacks
     */
    @Transactional
    public List<Feedback> getUnanswered(){
        List<Feedback> feedbacks = feedbackRepository.findByResponseIsNull();
        for(Feedback feedback: feedbacks){
            Hibernate.initialize(feedback.getUser());
        }
        return feedbacks;
    }

    /**
     * Save feedback if it's new or update it if it's already exists in database
     * @param feedback feedback which should be saved
     * @return saved or updated feedback
     */
    @Transactional
    public Feedback saveOrUpdate(Feedback feedback){
        return feedbackRepository.save(feedback);
    }

    /**
     * Delete feedback with id from database
     * @param id feedback's id
     */
    @Transactional
    public void deleteById(Long id){
        feedbackRepository.delete(id);
    }

    /**
     * Delete all feedback which where created before date from database
     * @param date all feedback which where created before this date will be deleted
     */
    @Transactional
    public void deleteAllBeforeDate(Date date){
        feedbackRepository.deleteAllByDateBefore(date);
    }

    /**
     * Delete feedback from database
     * @param feedback feedback which should be deleted
     */
    @Transactional
    public void delete(Feedback feedback){
        feedbackRepository.delete(feedback);
    }
}
