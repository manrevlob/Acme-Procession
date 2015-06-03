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

import services.RegistrationInvoiceService;
import services.StretchOrderService;
import utilities.AbstractTest;
import domain.RegistrationInvoice;
import domain.StretchOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class RegistrationInvoiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private RegistrationInvoiceService registrationInvoiceService;
	@Autowired
	private StretchOrderService stretchOrderService;

	// Tests -------------------------------------------------

	// Creamos y guardamos una nueva factura de registro
	@Test
	public void testCreateAndSave() {
		StretchOrder stretchOrder;
		RegistrationInvoice registrationInvoice;
		Collection<RegistrationInvoice> before;
		Collection<RegistrationInvoice> after;

		authenticate("brother1");

		before = registrationInvoiceService.findAll();

		// ID del stretch order 4
		stretchOrder = stretchOrderService.findOne(64);
		
		registrationInvoice = registrationInvoiceService.generateInvoice(stretchOrder);

		registrationInvoiceService.save(registrationInvoice);

		after = registrationInvoiceService.findAll();

		Assert.isTrue(after.size() > before.size());

		authenticate(null);
	}

	// Comprobamos que obtenemos todas las facturas de registros del hermano
	// logueado
	@Test
	public void testFindAllByBrother() {
		Collection<RegistrationInvoice> registrationInvoices;

		authenticate("brother1");

		registrationInvoices = registrationInvoiceService.findAllByBrother();
		
		Assert.isTrue(registrationInvoices.size() == 2);

		unauthenticate();
	}

	// Comprobamos que pagamos una factura
	@Test
	public void testPaidInvoice() {
		RegistrationInvoice registrationInvoice;

		authenticate("brother1");

		// ID del registrationInvoice 3
		registrationInvoice = registrationInvoiceService.findOneIfPrincipal(73);
		
		Assert.isTrue(registrationInvoiceService.findOneIfPrincipal(73).getPaidMoment() == null);

		registrationInvoiceService.paidInvoice(registrationInvoice);

		Assert.isTrue(registrationInvoiceService.findOneIfPrincipal(73).getPaidMoment() != null);

		unauthenticate();
	}

}
