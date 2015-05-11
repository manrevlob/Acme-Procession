package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Viewer;

@Repository
public interface ViewerRepository extends JpaRepository<Viewer, Integer> {

}
