package main;

import main.entities.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import main.repositories.PointRepository;

import java.util.List;

@SpringBootApplication
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
   /* @Autowired
     private PointRepository repository;
*/
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class);
    /*    Main main = new Main();
        main.doSomeBullShit();
    */
    }

    @Bean
    public CommandLineRunner demo(PointRepository repository){
        return (args)-> {
          doSomeBullShit(repository);
        };
    }

    private void doSomeBullShit(PointRepository repository){
        repository.save(new Point(1, 2, 3));
        repository.save(new Point(13, 6612236, 4));
        for(Point point: repository.findAll()){
            log.info(point.toString());
        }
        Point point = repository.findOne(1L);
        log.info("One point:");
        if(point!=null) log.info(point.toString());
        else log.info("null");
        List<Point> points = repository.findByX(13);
        log.info("Id:");
        for(Point thisPoint: points){
            log.info(thisPoint.getId().toString());
        }
    }
}
