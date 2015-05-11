package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Costume;

@Repository
public interface CostumeRepository extends JpaRepository<Costume, Integer> {

}
