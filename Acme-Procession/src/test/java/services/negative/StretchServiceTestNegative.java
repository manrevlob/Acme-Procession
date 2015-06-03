package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.ProcessionService;
import services.StretchService;
import utilities.AbstractTest;
import domain.Procession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class StretchServiceTestNegative extends AbstractTest {

	// Services under test --------------------------------------

	@Autowired
	private StretchService stretchService;

	@Autowired
	private ProcessionService processionService;

	// Tests ----------------------------------------------------

	// Probamos a traernos los tramos disponibles para una hermandad sin ser
	// hermanos mayores de la misma
	@Test(expected = IllegalArgumentException.class)
	public void testFindAvailablesNegative1() {
		Procession procession;

		authenticate("brother2");

		// ID de la procesión 1
		procession = processionService.findOneIfPrincipal(45);

		stretchService.findAvailables(procession);

		unauthenticate();
	}

	// Probamos a traernos los tramos disponibles para una hermandad siendo
	// viewers
	@Test(expected = IllegalArgumentException.class)
	public void testFindAvailablesNegative2() {
		Procession procession;

		authenticate("viewer1");

		// ID de la procesión 1
		procession = processionService.findOneIfPrincipal(45);

		stretchService.findAvailables(procession);

		unauthenticate();
	}

	// Probamos a traernos los tramos disponibles para una hermandad siendo
	// admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindAvailablesNegative3() {
		Procession procession;

		authenticate("admin");

		// ID de la procesión 1
		procession = processionService.findOneIfPrincipal(45);

		stretchService.findAvailables(procession);

		unauthenticate();
	}

	// Probamos a traernos los tramos disponibles para una hermandad sin estar
	// logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindAvailablesNegative4() {
		Procession procession;

		unauthenticate();

		// ID de la procesión 1
		procession = processionService.findOneIfPrincipal(45);

		stretchService.findAvailables(procession);
	}

}
