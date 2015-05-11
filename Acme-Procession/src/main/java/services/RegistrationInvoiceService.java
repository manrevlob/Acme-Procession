package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RegistrationInvoiceRepository;
import domain.RegistrationInvoice;

@Service
@Transactional
public class RegistrationInvoiceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RegistrationInvoiceRepository registrationInvoiceRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public RegistrationInvoiceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public RegistrationInvoice findOne(int registrationInvoiceId) {
		RegistrationInvoice result;

		result = registrationInvoiceRepository.findOne(registrationInvoiceId);

		return result;
	}

	public Collection<RegistrationInvoice> findAll() {
		Collection<RegistrationInvoice> result;

		result = registrationInvoiceRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
