package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BoxInstance;

@Repository
public interface BoxInstanceRepository extends
		JpaRepository<BoxInstance, Integer> {
	
	@Query("select b from BoxInstance b where b.box.id = ?1")
	Collection<BoxInstance> findByBox(int boxId);
	
	@Query("select b.date from BoxInstance b where b.box.id = ?1")
	Collection<Date> findDatesAvailablesByBox(int boxId);
}
