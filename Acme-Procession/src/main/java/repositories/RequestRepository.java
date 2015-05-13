package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r where r.brother.id = ?1")
	Collection<Request> findByBrotherId(int brotherId);
	
	@Query("select r.status from Request r where r.brother.id = ?1 order by r.creationMoment")
	Collection<String> AuthInnkeeper(int brotherId);
	
	@Query("select r from Request r where r.status = 'pending'")
	Collection<Request> findAllRequestPending();
}
