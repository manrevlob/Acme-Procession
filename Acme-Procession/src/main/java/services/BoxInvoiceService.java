package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.BoxInvoiceRepository;
import domain.BoxInvoice;

@Service
@Transactional
public class BoxInvoiceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxInvoiceRepository boxInvoiceRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BoxInvoiceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	
	public BoxInvoice findOne(int boxInvoiceId) {
		BoxInvoice result;

		result = boxInvoiceRepository.findOne(boxInvoiceId);

		return result;
	}

	public Collection<BoxInvoice> findAll() {
		Collection<BoxInvoice> result;

		result = boxInvoiceRepository.findAll();

		return result;
	}
	
	// Other business methods -------------------------------------------------

}
