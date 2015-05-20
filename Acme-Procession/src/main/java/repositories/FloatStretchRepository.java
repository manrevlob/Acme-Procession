package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.FloatStretch;

@Repository
public interface FloatStretchRepository extends
		JpaRepository<FloatStretch, Integer> {

	@Query("select fs from FloatStretch fs where fs.brotherhood IN ?1")
	Collection<FloatStretch> findMines(Collection<Brotherhood> brotherhoods);

	@Query("select fs from FloatStretch fs where fs.brotherhood = ?1")
	Collection<FloatStretch> findByBrotherhood(Brotherhood brotherhood);

}
