package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Carving;

@Repository
public interface CarvingRepository extends JpaRepository<Carving, Integer> {

	@Query("select c from Carving c where c.brotherhood.id = ?1")
	Collection<Carving> findByBrotherhood(int brotherhoodId);
	
}
