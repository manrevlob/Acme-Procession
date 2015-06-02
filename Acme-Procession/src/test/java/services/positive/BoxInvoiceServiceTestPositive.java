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

import services.BoxInvoiceService;
import utilities.AbstractTest;
import domain.BoxInvoice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class BoxInvoiceServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private BoxInvoiceService boxInvoiceService;

	// Tests -------------------------------------------------
	
	// Creamos un nuevo box
	@Test
	public void testPayInvoice() {
		BoxInvoice boxInvoice;
		BoxInvoice boxInvoiceAfter;

		authenticate("viewer1");
		
		// ID de la boxInvoice1
		boxInvoice = boxInvoiceService.findOne(91);

		boxInvoiceService.payInvoice(boxInvoice);

		boxInvoiceAfter = boxInvoiceService.findOne(91);

		Assert.isTrue(boxInvoiceAfter.getPaidMoment()!=null);

		authenticate(null);
	}
	
	// Probamos a obtener las boxInvoices del usuario logueado
	@Test
	public void testFindByPrincipal() {
		Collection<BoxInvoice> boxInvoices;
		
		authenticate("viewer1");

		boxInvoices = boxInvoiceService.findByPrincipal();

		Assert.isTrue(boxInvoices.size()==1);

		authenticate(null);
	}

}
