package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.BoxInvoiceService;
import utilities.AbstractTest;
import domain.BoxInvoice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BoxInvoiceServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private BoxInvoiceService boxInvoiceService;

	// Tests -------------------------------------------------
	
	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testPayInvoice() {
		BoxInvoice boxInvoice;

		authenticate("brother1");
		
		// ID de la boxInvoice1
		boxInvoice = boxInvoiceService.findOne(91);

		boxInvoiceService.payInvoice(boxInvoice);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testPayInvoice2() {
		BoxInvoice boxInvoice;

		authenticate("admin");
		
		// ID de la boxInvoice1
		boxInvoice = boxInvoiceService.findOne(91);

		boxInvoiceService.payInvoice(boxInvoice);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testPayInvoice3() {
		BoxInvoice boxInvoice;
		
		authenticate(null);
		
		// ID de la boxInvoice1
		boxInvoice = boxInvoiceService.findOne(91);

		boxInvoiceService.payInvoice(boxInvoice);		
	}
	
	// Comprobamos que no podemos pagar la misma factura dos veces
	@Test(expected = IllegalArgumentException.class)
	public void testPayInvoice4() {
		BoxInvoice boxInvoice;

		authenticate("viewer1");
		
		// ID de la boxInvoice1
		boxInvoice = boxInvoiceService.findOne(91);

		boxInvoiceService.payInvoice(boxInvoice);
		boxInvoiceService.payInvoice(boxInvoice);

		authenticate(null);
	}

	// Comprobamos que no podemos pagar una factura que no es nuestra
	@Test(expected = IllegalArgumentException.class) 
	public void testPayInvoice5() {
		BoxInvoice boxInvoice;

		authenticate("viewer2");
		
		// ID de la boxInvoice1 del viewer 1
		boxInvoice = boxInvoiceService.findOne(91);

		boxInvoiceService.payInvoice(boxInvoice);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal() {
		
		authenticate("brother1");

		boxInvoiceService.findByPrincipal();

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de administrator
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal2() {
		
		authenticate("admin");

		boxInvoiceService.findByPrincipal();

		authenticate(null);
	}
	
	// Comprobamos que no funcionasin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal3() {
		
		authenticate(null);

		boxInvoiceService.findByPrincipal();	
	}
}
