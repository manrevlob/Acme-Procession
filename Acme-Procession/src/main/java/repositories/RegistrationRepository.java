package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Registration;

@Repository
public interface RegistrationRepository extends
		JpaRepository<Registration, Integer> {

	@Query("select r from Registration r where r.procession.id = ?1 and r.stretch.id = ?2")
	Collection<Registration> findByProcessionIdAndStretchId(int processionId, int stretchId);

	@Query("select r from Registration r where r.procession.id = ?1 and r.brother.id = ?2")
	Collection<Registration> findByProcessionAndBrother(int processionId, int brotherId);
	
}
