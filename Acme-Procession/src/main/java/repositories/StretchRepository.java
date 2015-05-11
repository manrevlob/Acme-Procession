package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Stretch;

@Repository
public interface StretchRepository extends JpaRepository<Stretch, Integer> {

}
