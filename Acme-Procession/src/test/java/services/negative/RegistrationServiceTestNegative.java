package services.negative;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import services.RegistrationInvoiceService;
import services.RegistrationService;
import services.StretchOrderService;
import utilities.AbstractTest;
import domain.Registration;
import domain.RegistrationInvoice;
import domain.StretchOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RegistrationServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private StretchOrderService stretchOrderService;
	@Autowired
	private RegistrationInvoiceService registrationInvoiceService;

	// Tests -------------------------------------------------

	// Comprobamos que no podemos registrarnos en una procesion estando
	// logueados como viewer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave() {
		StretchOrder stretchOrder;
		RegistrationInvoice registrationInvoice;

		authenticate("viewer1");

		// ID del stretchOrder8
		stretchOrder = stretchOrderService.findOne(68);
		// ID de la registrationInvoice1
		registrationInvoice = registrationInvoiceService.findOne(71);

		registrationService.create(stretchOrder, registrationInvoice);

		unauthenticate();
	}

	// Comprobamos que no podemos registrarnos en una procesion estando
	// logueados como admin
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave2() {
		StretchOrder stretchOrder;
		RegistrationInvoice registrationInvoice;

		authenticate("admin");

		// ID del stretchOrder8
		stretchOrder = stretchOrderService.findOne(68);
		// ID de la registrationInvoice1
		registrationInvoice = registrationInvoiceService.findOne(71);

		registrationService.create(stretchOrder, registrationInvoice);

		unauthenticate();
	}

	// Comprobamos que no podemos registrarnos en una procesion sin estar
	// logueados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave3() {
		StretchOrder stretchOrder;
		RegistrationInvoice registrationInvoice;

		unauthenticate();

		// ID del stretchOrder8
		stretchOrder = stretchOrderService.findOne(68);
		// ID de la registrationInvoice1
		registrationInvoice = registrationInvoiceService.findOne(71);

		registrationService.create(stretchOrder, registrationInvoice);
	}

	// Comprobamos que no podemos registrarnos en una procesion que ha
	// finalizado su penitencia
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave4() {
		Registration registration; 
		StretchOrder stretchOrder;
		RegistrationInvoice registrationInvoice;

		authenticate("brother1");

		// ID del stretchOrder1
		stretchOrder = stretchOrderService.findOne(61);
		// ID de la registrationInvoice1
		registrationInvoice = registrationInvoiceService.findOne(71);

		registration = registrationService.create(stretchOrder, registrationInvoice);
		
		registrationService.save(registration);

		unauthenticate();
	}

	// Comprobamos que no podemos registrarnos en una procesion que ya estamos
	// registrados anteriormente
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave5() {
		Registration registration;
		StretchOrder stretchOrder;
		RegistrationInvoice registrationInvoice;

		authenticate("brother1");

		// ID del stretchOrder4
		stretchOrder = stretchOrderService.findOne(64);
		// ID de la registrationInvoice2
		registrationInvoice = registrationInvoiceService.findOne(73);

		registration = registrationService.create(stretchOrder, registrationInvoice);

		registrationService.save(registration);

		unauthenticate();
	}

	// Comprobamos que no podemos registrarnos en el tramo de paso de una
	// procesion
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave6() {
		StretchOrder stretchOrder;
		RegistrationInvoice registrationInvoice;

		authenticate("brother1");

		// ID del stretchOrder5
		stretchOrder = stretchOrderService.findOne(65);
		// ID de la registrationInvoice2
		registrationInvoice = registrationInvoiceService.findOne(73);

		registrationService.create(stretchOrder, registrationInvoice);

		unauthenticate();
	}

	// Comprobamos que no podemos obtener todos los registros si estas logueado
	// como viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrother() {
		int brotherId;

		authenticate("viewer1");

		// ID del brother1
		brotherId = 36;

		registrationService.findAllByBrother(brotherId);

		unauthenticate();
	}

	// Comprobamos que no podemos obtener todos los registros si estas logueado
	// como admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrother2() {
		int brotherId;

		authenticate("admin");

		// ID del brother1
		brotherId = 36;

		registrationService.findAllByBrother(brotherId);

		unauthenticate();
	}

	// Comprobamos que no podemos obtener todos los registros si estas logueado
	// como admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrother3() {
		int brotherId;

		unauthenticate();

		// ID del brother1
		brotherId = 36;

		registrationService.findAllByBrother(brotherId);
	}

	// Comprobamos que no podemos obtener un registro dada una factura si
	// estamos logueados como viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindByRegistrationInvoice() {
		int registrationInvoiceId;

		authenticate("viewer1");

		// ID de la registrationInvoice1
		registrationInvoiceId = 71;

		registrationService.findByRegistrationInvoice(registrationInvoiceId);

		unauthenticate();
	}

	// Comprobamos que no podemos obtener un registro dada una factura si
	// estamos logueados como admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindByRegistrationInvoice2() {
		int registrationInvoiceId;

		authenticate("admin");

		// ID de la registrationInvoice1
		registrationInvoiceId = 71;

		registrationService.findByRegistrationInvoice(registrationInvoiceId);

		unauthenticate();
	}

	// Comprobamos que no podemos obtener un registro dada una factura sino
	// estamos logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindByRegistrationInvoice3() {
		int registrationInvoiceId;

		unauthenticate();

		// ID de la registrationInvoice1
		registrationInvoiceId = 71;

		registrationService.findByRegistrationInvoice(registrationInvoiceId);
	}

}
