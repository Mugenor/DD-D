package main.controllers;

import main.entities.News;
import main.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(path = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {
    @Autowired
    private NewsService newsService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<News> getAllNews() {
        return newsService.getAllNews();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public News getNewsById(@PathVariable long id){
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
