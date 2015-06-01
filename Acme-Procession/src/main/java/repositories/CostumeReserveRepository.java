package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brother;
import domain.CostumeReserve;

@Repository
public interface CostumeReserveRepository extends
		JpaRepository<CostumeReserve, Integer> {

	@Query("select cr from CostumeReserve cr where cr.brother = ?1")
	Collection<CostumeReserve> findByPrincipal(Brother brother);

	@Query("select r from CostumeReserve r where r.costumeInvoice.id = ?1")
	CostumeReserve findByCostumeInvoice(int costumeInvoiceId);

}
