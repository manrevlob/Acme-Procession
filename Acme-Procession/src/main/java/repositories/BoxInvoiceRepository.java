package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BoxInvoice;

@Repository
public interface BoxInvoiceRepository extends
		JpaRepository<BoxInvoice, Integer> {
	
	@Query("select b.boxInvoice from BoxReserve b where b.viewer.id=?1")
	Collection<BoxInvoice> findByViewerId(int viewerId);

}
