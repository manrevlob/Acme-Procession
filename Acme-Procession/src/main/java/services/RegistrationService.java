package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RegistrationRepository;
import domain.Brother;
import domain.OrdinaryStretch;
import domain.Procession;
import domain.Registration;
import domain.RegistrationInvoice;
import domain.Stretch;
import domain.StretchOrder;

@Service
@Transactional
public class RegistrationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RegistrationRepository registrationRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private BrotherService brotherService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public RegistrationService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Registration create(StretchOrder stretchOrder,
			RegistrationInvoice registrationInvoice) {
		Registration result;
		Brother brother;
		long milliseconds;
		Date date;
		OrdinaryStretch ordinaryStretch;

		Assert.notNull(stretchOrder);
		Assert.notNull(registrationInvoice);
		Assert.isTrue(stretchOrder.getStretch().getClass() == OrdinaryStretch.class);

		brother = brotherService.findByPrincipal();
		ordinaryStretch = (OrdinaryStretch) stretchOrder.getStretch();

		milliseconds = System.currentTimeMillis();
		date = new Date(milliseconds - 10);
		result = new Registration();

		result.setBrother(brother);
		result.setProcession(stretchOrder.getProcession());
		result.setStretch(ordinaryStretch);
		result.setMoment(date);
		result.setRegistrationInvoice(registrationInvoice);

		return result;
	}

	public Registration findOne(int registrationId) {
		Registration result;

		result = registrationRepository.findOne(registrationId);

		return result;
	}

	public Collection<Registration> findAll() {
		Collection<Registration> result;

		result = registrationRepository.findAll();

		return result;
	}

	public void save(Registration registration) {
		Assert.notNull(registration);
		Assert.isTrue(actorService.isBrother());
		// Comprobamos que el registro no está cerrado
		Assert.isTrue(!registration.getProcession().getIsClosed(),
				"registration.registerIsClosed.error");
		// Comprobamos si el hermano ya esta registrado en esa misma procession
		Assert.isTrue(
				findByProcessionAndBrother(
						registration.getProcession().getId(),
						registration.getBrother().getId()).size() == 0,
				"registration.otherRegistrationCreated.error");

		registrationRepository.save(registration);
	}

	// Other business methods -------------------------------------------------

	public Collection<Registration> findByProcessionAndStretch(
			Procession procession, Stretch stretch) {
		Collection<Registration> result;

		result = registrationRepository.findByProcessionIdAndStretchId(
				procession.getId(), stretch.getId());

		return result;
	}

	public Collection<Registration> findByProcessionAndBrother(
			int processionId, int brotherId) {
		Collection<Registration> result;

		result = registrationRepository.findByProcessionAndBrother(
				processionId, brotherId);

		return result;
	}

	public Collection<Registration> findAllByBrother(int brotherId) {
		Collection<Registration> result;

		Assert.isTrue(actorService.isBrother());

		result = registrationRepository.findAllByBrother(brotherId);

		return result;
	}

	public Registration findByRegistrationInvoice(int registrationInvoiceId) {
		Registration result;

		Assert.isTrue(actorService.isBrother());

		result = registrationRepository
				.findByRegistrationInvoice(registrationInvoiceId);

		return result;
	}

}
