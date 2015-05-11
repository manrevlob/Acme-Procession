package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.BoxReserve;

@Repository
public interface BoxReserveRepository extends
		JpaRepository<BoxReserve, Integer> {

}
