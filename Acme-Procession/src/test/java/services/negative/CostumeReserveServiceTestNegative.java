package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.CostumeReserveService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CostumeReserveServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private CostumeReserveService costumeReserveService;
	
	// Tests -------------------------------------------------
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal() {
		
		authenticate("viewer1");
		
		//ID de la costumeReserve 1
		costumeReserveService.findOneIfPrincipal(107);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal2() {
		
		authenticate("admin");
		
		//ID de la costumeReserve 1
		costumeReserveService.findOneIfPrincipal(107);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal3() {
		
		authenticate(null);
		
		//ID de la costumeReserve 1
		costumeReserveService.findOneIfPrincipal(107);	
	}
	
	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal() {
		
		authenticate("admin");
			
		costumeReserveService.findByPrincipal();

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal2() {
		
		authenticate("viewer1");
			
		costumeReserveService.findByPrincipal();

		authenticate(null);
	}

	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal3() {
		
		authenticate(null);
			
		costumeReserveService.findByPrincipal();	
	}
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindByCostumeInvoice() {
		
		authenticate("viewer1");
		
		//ID de la costumeInvoice 1
		costumeReserveService.findByCostumeInvoice(108);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindByCostumeInvoice2() {
		
		authenticate("admin");
		
		//ID de la costumeInvoice 1
		costumeReserveService.findByCostumeInvoice(108);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindByCostumeInvoice3() {
		
		authenticate(null);
		
		//ID de la costumeInvoice 1
		costumeReserveService.findByCostumeInvoice(108);
	}
	
}
