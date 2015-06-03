package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RegistrationInvoiceRepository;
import domain.Brother;
import domain.Money;
import domain.OrdinaryStretch;
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

//		Assert.isTrue(actorService.isBrother());

		result = registrationInvoiceRepository.findOne(registrationInvoiceId);
//		checkIfPrincipal(result);

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
		Assert.isTrue(stretchOrder.getStretch().getClass() == OrdinaryStretch.class);

		result = create(stretchOrder);

		return result;
	}
	
	public RegistrationInvoice findOneIfPrincipal(int registrationInvoiceId) {
		RegistrationInvoice result;
		
		Assert.isTrue(actorService.isBrother());
		
		result = findOne(registrationInvoiceId);
		
		checkIfPrincipal(result);

		return result;
	}

	public void paidInvoice(RegistrationInvoice registrationInvoice) {
		long milliseconds;
		Date date;
		
		Assert.isTrue(actorService.isBrother());
		Assert.isTrue(registrationInvoice.getPaidMoment() == null);

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

	public void checkIfPrincipal(RegistrationInvoice registrationInvoice) {
		Brother brother;
		Collection<RegistrationInvoice> registrationInvoices;

		Assert.isTrue(actorService.isBrother());

		brother = brotherService.findByPrincipal();

		registrationInvoices = registrationInvoiceRepository
				.findAllByBrother(brother.getId());

		Assert.isTrue(registrationInvoices.contains(registrationInvoice));

	}

}
