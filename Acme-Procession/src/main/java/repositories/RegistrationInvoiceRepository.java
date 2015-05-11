package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.RegistrationInvoice;

@Repository
public interface RegistrationInvoiceRepository extends
		JpaRepository<RegistrationInvoice, Integer> {

}
