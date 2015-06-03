package services.positive;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ProcessionService;
import services.RegistrationInvoiceService;
import services.RegistrationService;
import services.StretchOrderService;
import services.StretchService;
import utilities.AbstractTest;
import domain.Procession;
import domain.Registration;
import domain.RegistrationInvoice;
import domain.Stretch;
import domain.StretchOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class RegistrationServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private StretchOrderService stretchOrderService;
	@Autowired
	private RegistrationInvoiceService registrationInvoiceService;
	@Autowired
	private ProcessionService processionService;
	@Autowired
	private StretchService stretchService;

	// Tests -------------------------------------------------

	// Creamos un nuevo registro como brother
	@Test
	public void testCreateAndSave() {
		Collection<Registration> before;
		Collection<Registration> after;
		Registration registration;
		StretchOrder stretchOrder;
		RegistrationInvoice registrationInvoice;

		authenticate("brother5");

		before = registrationService.findAll();

		// ID del stretchOrder8
		stretchOrder = stretchOrderService.findOne(68);
		// ID de la registrationInvoice1
		registrationInvoice = registrationInvoiceService.findOne(71);

		registration = registrationService.create(stretchOrder,
				registrationInvoice);

		registrationService.save(registration);

		after = registrationService.findAll();

		unauthenticate();

		Assert.isTrue(before.size() < after.size());
	}

	// Comprobamos que obtenemos los registros dada una procesion y un tramo
	@Test
	public void testFindByProcessionAndStretch() {
		Collection<Registration> registrations;
		Procession procession;
		Stretch stretch;

		// ID de la procession2
		procession = processionService.findOne(46);

		// ID de la ordinaryStretch1
		stretch = stretchService.findOne(53);

		registrations = registrationService.findByProcessionAndStretch(
				procession, stretch);

		Assert.isTrue(registrations.size() == 1);

	}

	// Comprobamos que obtenemos los registros dado una procesion y un hermano
	@Test
	public void testFindByProcessionAndBrother() {
		Collection<Registration> registrations;
		int processionId;
		int brotherId;

		// ID de la procession2
		processionId = 46;
		// ID del brother1
		brotherId = 36;

		registrations = registrationService.findByProcessionAndBrother(
				processionId, brotherId);

		Assert.isTrue(registrations.size() == 1);
	}

	// Obtenemos todos los registros dado un hermano
	@Test
	public void testFindAllByBrother() {
		Collection<Registration> registrations;
		int brotherId;

		authenticate("brother1");

		// ID del brother1
		brotherId = 36;

		registrations = registrationService.findAllByBrother(brotherId);

		unauthenticate();

		Assert.isTrue(registrations.size() == 2);
	}

	// Obtenemos el registro dado una factura del registro
	@Test
	public void testFindByRegistrationInvoice() {
		Registration registration;
		int registrationInvoiceId;

		authenticate("brother1");

		// ID de la registrationInvoice1
		registrationInvoiceId = 71;

		registration = registrationService
				.findByRegistrationInvoice(registrationInvoiceId);

		unauthenticate();

		Assert.isTrue(registration.getRegistrationInvoice().equals(
				registrationInvoiceService.findOne(registrationInvoiceId)));
	}

}
