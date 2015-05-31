package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.ViewerService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ViewerServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private ViewerService viewerService;

	// Tests -------------------------------------------------
	
	// Probamos que no devuelve usuario sino hay un usuario logueado
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal() {
		unauthenticate();

		viewerService.findByPrincipal();
	}

	// Comprobamos que no funciona con el rol de administrator
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal2() {
		authenticate("admin");
		
		viewerService.findByPrincipal();

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal3() {
		authenticate("brother1");
		
		viewerService.findByPrincipal();

		authenticate(null);
	}
	

}
