package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.OrdinaryStretch;

@Repository
public interface OrdinaryStretchRepository extends
		JpaRepository<OrdinaryStretch, Integer> {

}
