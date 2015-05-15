package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.OrdinaryStretch;

@Repository
public interface OrdinaryStretchRepository extends
		JpaRepository<OrdinaryStretch, Integer> {

	@Query("select os from OrdinaryStretch os where os.brotherhood IN ?1")
	Collection<OrdinaryStretch> findMines(Collection<Brotherhood> brotherhoods);

}
