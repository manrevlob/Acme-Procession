package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CostumeInvoiceRepository;
import domain.Costume;
import domain.CostumeInvoice;
import domain.Money;

@Service
@Transactional
public class CostumeInvoiceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CostumeInvoiceRepository costumeInvoiceRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private BrotherService brotherService;

	// Constructors -----------------------------------------------------------

	public CostumeInvoiceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public CostumeInvoice create(Costume costume, String type) {
		CostumeInvoice result;
		long milliseconds;
		Date date;
		Money totalCost;

		milliseconds = System.currentTimeMillis();
		date = new Date(milliseconds - 10);

		if (type == "purchase")
			totalCost = costume.getSalePrice();
		else
			totalCost = costume.getRentalPrice();

		result = new CostumeInvoice();

		result.setCreateMoment(date);
		result.setTotalCost(totalCost);

		return result;
	}

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

	public void save(CostumeInvoice costumeInvoice) {
		Assert.notNull(costumeInvoice);

		costumeInvoiceRepository.save(costumeInvoice);
	}

	// Other business methods -------------------------------------------------

	public Collection<CostumeInvoice> findAllByBrother() {
		Collection<CostumeInvoice> results;
		int brotherId;

		Assert.isTrue(actorService.isBrother());

		brotherId = brotherService.findByPrincipal().getId();

		results = costumeInvoiceRepository.findAllByBrother(brotherId);

		return results;
	}

	public void paidInvoice(CostumeInvoice costumeInvoice) {
		long milliseconds;
		Date date;
		Assert.isTrue(actorService.isBrother());

		milliseconds = System.currentTimeMillis();
		date = new Date(milliseconds - 10);

		costumeInvoice.setPaidMoment(date);

		save(costumeInvoice);

	}

	public CostumeInvoice generateInvoice(Costume costume, String type) {
		CostumeInvoice result;

		Assert.isTrue(actorService.isBrother());

		result = create(costume, type);

		return result;
	}

}
