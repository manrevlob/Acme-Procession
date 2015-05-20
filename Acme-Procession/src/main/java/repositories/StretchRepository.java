package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;
import domain.Procession;
import domain.Stretch;

@Repository
public interface StretchRepository extends JpaRepository<Stretch, Integer> {

	@Query("select s from Stretch s where s.brotherhood = ?1 and s NOT IN (select so.stretch from StretchOrder so where so.procession = ?2)")
	Collection<Stretch> findAvailableStretches(Brotherhood brotherhood, Procession procession);

}
