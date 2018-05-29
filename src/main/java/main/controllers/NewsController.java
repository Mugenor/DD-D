package main.controllers;

import main.entities.News;
import main.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {
    private NewsService newsService;

    public NewsController(){}

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<News> getAllNews() {
        return newsService.getAllNews();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public News getNewsById(@PathVariable Integer id){
        return newsService.getNewsById(id);
    }

    @RequestMapping(path = "/topic/{topic}", method = RequestMethod.GET)
    public Iterable<News> getNewsByTopic(@PathVariable String topic){
        return newsService.getNewsByTopic(topic);
    }
/*
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveNews(@RequestBody News news){
        news.setDate(new Date());
        newsService.saveOrUpdate(news);
    }*/
}
