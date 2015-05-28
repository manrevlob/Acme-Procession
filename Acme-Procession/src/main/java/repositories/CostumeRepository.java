package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Costume;

@Repository
public interface CostumeRepository extends JpaRepository<Costume, Integer> {

	@Query("select c from Costume c where c.brotherhood = ?1")
	Collection<Costume> findByBrotherhood(Brotherhood brotherhood);

	@Query("select c from Costume c where c.brotherhood = ?1 and c.situation like 'available' and c.size >= ?2 and c.size <= ?3")
	Collection<Costume> findAvailablesByBrotherhoodAndSize(Brotherhood brotherhood, int minSize, int maxSize);

}
