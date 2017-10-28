package main.repositories;

import main.entities.Point;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointRepository extends CrudRepository<Point, Long> {
    public List<Point> findByX(double x);
}
