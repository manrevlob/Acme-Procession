package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Procession;

@Repository
public interface ProcessionRepository extends
		JpaRepository<Procession, Integer> {

	@Query("select p from Procession p where p.brotherhood = ?1")
	Collection<Procession> findByBrotherhood(Brotherhood brotherhood);

	@Query("select p from Procession p where p.isClosedManually is false and datediff(CURRENT_TIMESTAMP, p.startMoment) < 7")
	Collection<Procession> findAllAvailables();
	
	@Query("select p from Procession p where p.endMoment <= ?1")
	Collection<Procession> findAllPastDate(Date date);
	
	// Dashboard
	@Query("select p from Procession p order by p.registrations.size desc")
	Collection<Procession> findAllOrderByNumReg();
	
}
