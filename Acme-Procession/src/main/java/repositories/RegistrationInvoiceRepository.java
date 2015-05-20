package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.RegistrationInvoice;

@Repository
public interface RegistrationInvoiceRepository extends
		JpaRepository<RegistrationInvoice, Integer> {

	@Query("select ri from Registration r join r.registrationInvoice ri where r.brother.id = ?1")
	Collection<RegistrationInvoice> findAllByBrother(int brotherId);
	
}
