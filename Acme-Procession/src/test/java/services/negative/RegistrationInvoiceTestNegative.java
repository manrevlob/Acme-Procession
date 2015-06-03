package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.RegistrationInvoiceService;
import services.StretchOrderService;
import utilities.AbstractTest;
import domain.RegistrationInvoice;
import domain.StretchOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RegistrationInvoiceTestNegative extends AbstractTest {
	// Service under test ------------------------------------

	@Autowired
	private RegistrationInvoiceService registrationInvoiceService;
	@Autowired
	private StretchOrderService stretchOrderService;

	// Tests -------------------------------------------------

	// Intentamos crear y guardar una invoice de registro asociada a un tramo de
	// paso (no se puede)
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative() {
		StretchOrder stretchOrder;
		RegistrationInvoice registrationInvoice;

		authenticate("brother1");

		// ID del stretch order 5
		stretchOrder = stretchOrderService.findOne(65);

		registrationInvoice = registrationInvoiceService
				.generateInvoice(stretchOrder);

		registrationInvoiceService.save(registrationInvoice);

		authenticate(null);
	}

	// Comprobamos que no podemos obtener las facturas si nos logeamos como
	// viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrotherNegative1() {
		authenticate("viewer1");

		registrationInvoiceService.findAllByBrother();

		unauthenticate();
	}

	// Comprobamos que no podemos obtener las facturas si nos logeamos como
	// viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrotherNegative2() {
		authenticate("admin");

		registrationInvoiceService.findAllByBrother();

		unauthenticate();
	}

	// Comprobamos que no podemos obtener las facturas si nos logeamos como
	// administrador
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrotherNegative3() {
		authenticate("admin");

		registrationInvoiceService.findAllByBrother();

		unauthenticate();
	}

	// Comprobamos que no podemos obtener las facturas si no estamos logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrotherNegative4() {
		unauthenticate();

		registrationInvoiceService.findAllByBrother();
	}

	// Comprobamos que NO podemos pagar una factura que ya está pagada
	@Test(expected = IllegalArgumentException.class)
	public void testPaidInvoiceNegative1() {
		RegistrationInvoice registrationInvoice;

		authenticate("brother1");

		// ID del registrationInvoice 1
		registrationInvoice = registrationInvoiceService.findOneIfPrincipal(71);

		registrationInvoiceService.paidInvoice(registrationInvoice);

		unauthenticate();
	}

	// Comprobamos que NO podemos pagar una factura que no sea nuestra
	@Test(expected = IllegalArgumentException.class)
	public void testPaidInvoiceNegative2() {
		RegistrationInvoice registrationInvoice;

		authenticate("brother1");

		// ID del registrationInvoice 4
		registrationInvoice = registrationInvoiceService.findOneIfPrincipal(74);

		registrationInvoiceService.paidInvoice(registrationInvoice);

		unauthenticate();
	}
}
