package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Brother;

@Repository
public interface BrotherRepository extends JpaRepository<Brother, Integer> {

}
