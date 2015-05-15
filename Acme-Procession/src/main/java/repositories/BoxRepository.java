package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {
	
	@Query("select b from Box b where b.administrator.id=?1")
	Collection<Box> findByAdministratorId(int administratorId);

}
