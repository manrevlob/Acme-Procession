package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RegistrationInvoiceRepository;
import domain.Money;
import domain.RegistrationInvoice;
import domain.StretchOrder;

@Service
@Transactional
public class RegistrationInvoiceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RegistrationInvoiceRepository registrationInvoiceRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherService brotherService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public RegistrationInvoiceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public RegistrationInvoice create(StretchOrder stretchOrder) {
		RegistrationInvoice result;
		long milliseconds;
		Date date;
		Money totalCost;

		milliseconds = System.currentTimeMillis();
		date = new Date(milliseconds - 10);

		totalCost = stretchOrder.getProcession().getAssociatedCost();

		result = new RegistrationInvoice();

		result.setCreateMoment(date);
		result.setTotalCost(totalCost);

		return result;
	}

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

	public void save(RegistrationInvoice registrationInvoice) {
		Assert.notNull(registrationInvoice);

		registrationInvoiceRepository.save(registrationInvoice);
	}

	// Other business methods -------------------------------------------------

	public RegistrationInvoice generateInvoice(StretchOrder stretchOrder) {
		RegistrationInvoice result;

		Assert.isTrue(actorService.isBrother());

		result = create(stretchOrder);

		return result;
	}

	public void paidInvoice(RegistrationInvoice registrationInvoice) {
		long milliseconds;
		Date date;
		Assert.isTrue(actorService.isBrother());

		milliseconds = System.currentTimeMillis();
		date = new Date(milliseconds - 10);

		registrationInvoice.setPaidMoment(date);

		save(registrationInvoice);
	}

	public Collection<RegistrationInvoice> findAllByBrother() {
		Collection<RegistrationInvoice> results;
		int brotherId;

		Assert.isTrue(actorService.isBrother());

		brotherId = brotherService.findByPrincipal().getId();

		results = registrationInvoiceRepository.findAllByBrother(brotherId);

		return results;
	}

}
