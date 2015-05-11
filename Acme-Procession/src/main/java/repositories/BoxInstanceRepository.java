package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.BoxInstance;

@Repository
public interface BoxInstanceRepository extends
		JpaRepository<BoxInstance, Integer> {

}
