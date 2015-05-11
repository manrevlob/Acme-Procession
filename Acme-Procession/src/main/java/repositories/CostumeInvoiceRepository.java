package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.CostumeInvoice;

@Repository
public interface CostumeInvoiceRepository extends
		JpaRepository<CostumeInvoice, Integer> {

}
