package main.repositories;

import main.entities.Feature;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends CrudRepository<Feature, Integer> {
}
