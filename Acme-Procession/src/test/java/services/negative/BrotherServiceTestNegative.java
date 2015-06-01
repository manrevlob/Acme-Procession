package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.BrotherService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BrotherServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private BrotherService brotherService;

	// Tests -------------------------------------------------
	
	// Probamos que no devuelve usuario sino hay un usuario logueado
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal() {
		unauthenticate();

		brotherService.findByPrincipal();
	}

	// Comprobamos que no funciona con el rol de administrator
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal2() {
		authenticate("admin");
		
		brotherService.findByPrincipal();

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal3() {
		authenticate("viewer1");
		
		brotherService.findByPrincipal();

		authenticate(null);
	}
	

}
