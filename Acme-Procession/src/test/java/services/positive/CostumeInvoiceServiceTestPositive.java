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

import services.CostumeInvoiceService;
import services.CostumeService;
import utilities.AbstractTest;
import domain.Costume;
import domain.CostumeInvoice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CostumeInvoiceServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private CostumeInvoiceService costumeInvoiceService;
	@Autowired
	private CostumeService costumeService;

	// Tests -------------------------------------------------

	// Creamos y guardamos una nueva factura de costume
	@Test
	public void testCreateAndSave() {
		Costume costume;
		CostumeInvoice costumeInvoice;
		String type;
		Collection<CostumeInvoice> before;
		Collection<CostumeInvoice> after;

		authenticate("brother1");

		before = costumeInvoiceService.findAll();

		// ID del Costume1
		costume = costumeService.findOne(99);
		type = "purchase";

		costumeInvoice = costumeInvoiceService.generateInvoice(costume, type);

		costumeInvoiceService.save(costumeInvoice);

		after = costumeInvoiceService.findAll();

		Assert.isTrue(after.size() > before.size());

		authenticate(null);
	}

	// Comprobamos que obtenemos todas las facturas de trajes del hermano logueado
	@Test
	public void testFindAllByBrother() {
		Collection<CostumeInvoice> costumeInvoices;

		authenticate("brother1");

		costumeInvoices = costumeInvoiceService.findAllByBrother();

		// ID del costumeInvoice1 que pertenece al brother1
		Assert.isTrue(costumeInvoices.contains(costumeInvoiceService
				.findOne(108)));
		
		unauthenticate();
	}

	// Comprobamos que pagamos una factura
	@Test
	public void testPaidInvoice() {
		CostumeInvoice costumeInvoice;

		authenticate("brother1");

		// ID del costumeInvoice1
		costumeInvoice = costumeInvoiceService.findOne(108);

		costumeInvoiceService.paidInvoice(costumeInvoice);

		Assert.isTrue(costumeInvoiceService.findOne(108).getPaidMoment() != null);
		
		unauthenticate();
	}

}
