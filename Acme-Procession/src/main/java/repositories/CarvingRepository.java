package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Carving;
import domain.Procession;
import domain.Stretch;

@Repository
public interface CarvingRepository extends JpaRepository<Carving, Integer> {

	@Query("select c from Carving c where c.brotherhood.id = ?1")
	Collection<Carving> findByBrotherhood(int brotherhoodId);

	@Query("select c from Carving c join c.associatedStretchs fs join fs.stretchOrders so join so.procession p where p = ?1 group by c")
	Collection<Carving> findByProcession(Procession procession);

	@Query("select fs.carvings from FloatStretch fs where fs = ?1")
	Collection<Carving> findByStretch(Stretch stretch);

}
