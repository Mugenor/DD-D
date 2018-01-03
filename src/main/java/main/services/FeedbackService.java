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
@Transactional
public class FeedbackService {
    private FeedbackRepository feedbackRepository;

    public FeedbackService(){}

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository){
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Find and return one Feedback by id from the database
     * @param id of required Feedback
     * @return one Feedback
     */
    public Feedback getById(Long id){
        Feedback feedback = feedbackRepository.findOne(id);
        if(feedback!=null) {
            Hibernate.initialize(feedback.getUser());
        }
        return feedback;
    }

    /**
     * Find and return one Feedback by username from the database
     * @param username of required Feedback
     * @return one Feedback
     */
    public List<Feedback> getByUserName(String username){
        List<Feedback> feedbacks = feedbackRepository.findByUserUsername(username);
        for(Feedback feedback: feedbacks){
            Hibernate.initialize(feedback.getUser());
        }
        return feedbacks;
    }

    /**
     * Find and return all unanswered Feedbacks from the database
     * @return the list of unanswered Feedbacks
     */
    public List<Feedback> getUnanswered(){
        List<Feedback> feedbacks = feedbackRepository.findByResponseIsNull();
        for(Feedback feedback: feedbacks){
            Hibernate.initialize(feedback.getUser());
        }
        return feedbacks;
    }

    /**
     * Return all Feedbacks from the database
     * @return Feedbacks
     */
    public Iterable<Feedback> getAllFeebacks(){
        Iterable<Feedback> feedbacks = feedbackRepository.findAll();
        for(Feedback feedback: feedbacks){
            Hibernate.initialize(feedback.getUser());
        }
        return feedbacks;
    }

    /**
     * Save the Feedback if it's a new or update if it already exists
     * @param feedback which should be saved or updated
     * @return saved or updated feedback
     */
    public Feedback saveOrUpdate(Feedback feedback){
        return feedbackRepository.save(feedback);
    }

    /**
     * Delete the feedback by id from the database
     * @param id of required Feedback
     */
    public void deleteById(Long id){
        feedbackRepository.delete(id);
    }

    /**
     * Delete all feedbacks which were created before the required date from the database
     * @param date all feedbacks which were created before this date will be deleted
     */
    public void deleteAllBeforeDate(Date date){
        feedbackRepository.deleteAllByDateBefore(date);
    }

    /**
     * Delete the required Feedback from the database
     * @param feedback which should be deleted
     */
    public void delete(Feedback feedback){
        feedbackRepository.delete(feedback);
    }
}
