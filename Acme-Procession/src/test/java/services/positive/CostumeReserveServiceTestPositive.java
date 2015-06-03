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

import security.LoginService;
import services.CostumeReserveService;
import utilities.AbstractTest;
import domain.CostumeReserve;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CostumeReserveServiceTestPositive extends AbstractTest {

	@Autowired
	private CostumeReserveService costumeReserveService;

	// Tests -------------------------------------------------

	// Probamos a obtener un costume 
	@Test
	public void testFindOneIfPrincipal() {
		CostumeReserve costumeReserve;
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
		
		//ID de la costumeReserve 1
		costumeReserve = costumeReserveService.findOneIfPrincipal(107);

		Assert.notNull(LoginService.getPrincipal().equals(costumeReserve.getBrother()));

		authenticate(null);
	}
	
	// Probamos a obtener una colleccion de costumes
	@Test
	public void testFindByPrincipal() {
		Collection<CostumeReserve> costumeReserves;
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
			
		costumeReserves = costumeReserveService.findByPrincipal();

		Assert.isTrue(costumeReserves.size()==2);

		authenticate(null);
	}
	
	// Probamos a obtener un costume a partir de su factura
	@Test
	public void testFindByCostumeInvoice() {
		CostumeReserve costume;
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
		
		//ID de la costumeInvoice 1
		costume = costumeReserveService.findByCostumeInvoice(108);

		Assert.notNull(costume);

		authenticate(null);
	}

}
