package main.repositories;

import main.entities.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PointRepository extends CrudRepository<Point, Long> {
    public List<Point> findByX(double x);
}
