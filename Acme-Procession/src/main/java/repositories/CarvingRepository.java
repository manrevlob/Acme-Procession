package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Carving;

@Repository
public interface CarvingRepository extends JpaRepository<Carving, Integer> {

}
