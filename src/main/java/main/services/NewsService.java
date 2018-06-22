package main.services;

import main.entities.News;
import main.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class NewsService {
    private NewsRepository newsRepository;

    public NewsService(){}

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News getNewsById(int id){
        return newsRepository.findOne(id);
    }

    public Iterable<News> getNewsByTopic(String topic){
        return newsRepository.getAllByTopic(topic);
    }

    public Iterable<News> getNewsAfterDate(Date after){
        return newsRepository.getAllByDateAfter(after);
    }

    public Iterable<News> getAllNews(){
        return newsRepository.findAll();
    }

    public News saveOrUpdate(News news){
        return newsRepository.save(news);
    }

    public Iterable<News> saveOrUpdate(Iterable<News> news){
        return newsRepository.save(news);
    }

    public void deleteNewsById(int id){
        newsRepository.delete(id);
    }

    public void deleteNewsByTopic(String topic){
        newsRepository.deleteAllByTopic(topic);
    }

    public void deleteNewsBeforeDate(Date before){
        newsRepository.deleteAllByDateBefore(before);
    }
}
