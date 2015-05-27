package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BoxReserve;

@Repository
public interface BoxReserveRepository extends
		JpaRepository<BoxReserve, Integer> {
	
	@Query("select b from BoxReserve b where b.viewer.id=?1")
	Collection<BoxReserve> findByViewerId(int viewerId);
	
	@Query("select b from BoxReserve b where b.boxInvoice.id=?1")
	BoxReserve findByBoxInvoice(int boxInvoiceId);
	

}
