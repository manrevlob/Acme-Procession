package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CostumeInvoiceRepository;
import domain.CostumeInvoice;

@Service
@Transactional
public class CostumeInvoiceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CostumeInvoiceRepository costumeInvoiceRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CostumeInvoiceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public CostumeInvoice findOne(int costumeInvoiceId) {
		CostumeInvoice result;

		result = costumeInvoiceRepository.findOne(costumeInvoiceId);

		return result;
	}

	public Collection<CostumeInvoice> findAll() {
		Collection<CostumeInvoice> result;

		result = costumeInvoiceRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
