package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.ActorService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ActorServiceTestNegative extends AbstractTest {
	// Service under test ------------------------------------
	@Autowired
	private ActorService actorService;

	// Tests -------------------------------------------------

	// Comprobamos que el método falla al no estar logueado
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipalNegative() {
		unauthenticate();

		actorService.findByPrincipal();
	}

	// Comprobamos que no funciona sin estar logueado
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllExceptMeNegative() {
		unauthenticate();

		actorService.findAllExceptMe();
	}
}
