package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CostumeReserve;

@Repository
public interface CostumeReserveRepository extends
		JpaRepository<CostumeReserve, Integer> {

}
