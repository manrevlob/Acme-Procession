package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CostumeInvoice;

@Repository
public interface CostumeInvoiceRepository extends
		JpaRepository<CostumeInvoice, Integer> {

	 @Query("select ci from CostumeReserve c join c.costumeInvoice ci where c.brother.id = ?1")
	 Collection<CostumeInvoice> findAllByBrother(int brotherId);

}
