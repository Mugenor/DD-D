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

    @Transactional
    public Feedback getById(Long id){
        Feedback feedback = feedbackRepository.findOne(id);
        Hibernate.initialize(feedback.getUser());
        return feedback;
    }
    @Transactional
    public List<Feedback> getByUserName(String username){
        List<Feedback> feedbacks = feedbackRepository.findByUser_Username(username);
        for(Feedback feedback: feedbacks){
            Hibernate.initialize(feedback.getUser());
        }
        return feedbacks;
    }
    @Transactional
    public List<Feedback> getUnanswered(){
        List<Feedback> feedbacks = feedbackRepository.findByResponseIsNull();
        for(Feedback feedback: feedbacks){
            Hibernate.initialize(feedback.getUser());
        }
        return feedbacks;
    }
    @Transactional
    public Feedback saveOrUpdate(Feedback feedback){
        return feedbackRepository.save(feedback);
    }
    @Transactional
    public void deleteById(Long id){
        feedbackRepository.delete(id);
    }
    @Transactional
    public void deleteAllBeforeDate(Date date){
        feedbackRepository.deleteAllByDateBefore(date);
    }
}
