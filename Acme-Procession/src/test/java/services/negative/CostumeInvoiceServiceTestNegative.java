package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.CostumeInvoiceService;
import services.CostumeService;
import utilities.AbstractTest;
import domain.Costume;
import domain.CostumeInvoice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CostumeInvoiceServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private CostumeInvoiceService costumeInvoiceService;
	@Autowired
	private CostumeService costumeService;

	// Tests -------------------------------------------------

	// Comprobamos que no podemos crear una factura de un traje con el rol de
	// viewer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave() {
		Costume costume;
		String type;

		authenticate("viewer1");

		// ID del Costume1
		costume = costumeService.findOne(99);
		type = "purchase";

		costumeInvoiceService.generateInvoice(costume, type);

		authenticate(null);
	}

	// Comprobamos que no podemos crear una factura de un traje con el rol de
	// admin
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave2() {
		Costume costume;
		String type;

		authenticate("admin");

		// ID del Costume1
		costume = costumeService.findOne(99);
		type = "purchase";

		costumeInvoiceService.generateInvoice(costume, type);

		authenticate(null);
	}

	// Comprobamos que no podemos crear una factura de un traje sin estar
	// logueados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave3() {
		Costume costume;
		String type;

		unauthenticate();

		// ID del Costume1
		costume = costumeService.findOne(99);
		type = "purchase";

		costumeInvoiceService.generateInvoice(costume, type);
	}

	// Comprobamos que no podemos obtener las facturas de un brother siendo
	// viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrother() {
		authenticate("viewer");

		costumeInvoiceService.findAllByBrother();

		unauthenticate();
	}

	// Comprobamos que no podemos obtener las facturas de un brother siendo
	// admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrother2() {
		authenticate("admin");

		costumeInvoiceService.findAllByBrother();

		unauthenticate();
	}

	// Comprobamos que no podemos obtener las facturas de un brother sin estar
	// logueado
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByBrother3() {
		unauthenticate();

		costumeInvoiceService.findAllByBrother();
	}

	// Comprobamos que no podemos pagar una factura si estamos logueados como
	// viewer
	@Test(expected = IllegalArgumentException.class)
	public void testPaidInvoice() {
		CostumeInvoice costumeInvoice;

		authenticate("viewer1");

		// ID del costumeInvoice2
		costumeInvoice = costumeInvoiceService.findOne(110);

		costumeInvoiceService.paidInvoice(costumeInvoice);

		unauthenticate();
	}

	// Comprobamos que no podemos pagar una factura si estamos logueados como
	// admin
	@Test(expected = IllegalArgumentException.class)
	public void testPaidInvoice2() {
		CostumeInvoice costumeInvoice;

		authenticate("admin");

		// ID del costumeInvoice2
		costumeInvoice = costumeInvoiceService.findOne(110);

		costumeInvoiceService.paidInvoice(costumeInvoice);

		unauthenticate();
	}

	// Comprobamos que no podemos pagar una factura sino estamos logueados
	@Test(expected = IllegalArgumentException.class)
	public void testPaidInvoice3() {
		CostumeInvoice costumeInvoice;

		unauthenticate();

		// ID del costumeInvoice2
		costumeInvoice = costumeInvoiceService.findOne(110);

		costumeInvoiceService.paidInvoice(costumeInvoice);
	}

}
