package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Assessment;

@Repository
public interface AssessmentRepository extends
		JpaRepository<Assessment, Integer> {

	@Query("select a from Assessment a where a.viewer.id = ?1")
	Collection<Assessment> findAllByViewer(int viewerId);
	
}
