package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brother;
import domain.Brotherhood;

@Repository
public interface BrotherhoodRepository extends
		JpaRepository<Brotherhood, Integer> {

	@Query("select b from Brotherhood b where ?1 MEMBER OF b.brothers")
	Collection<Brotherhood> findMines(Brother brother);

	@Query("select b from Brotherhood b where ?1 MEMBER OF b.bigBrothers")
	Collection<Brotherhood> findOwns(Brother brother);
	
	@Query("select b from Brotherhood b where b.logo.id = ?1")
	Brotherhood findByImage(int logoId);

}
