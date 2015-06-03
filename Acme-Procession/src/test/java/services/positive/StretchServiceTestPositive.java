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

import services.ProcessionService;
import services.StretchService;
import utilities.AbstractTest;
import domain.Procession;
import domain.Stretch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class StretchServiceTestPositive extends AbstractTest {
	// Services under test --------------------------------------

	@Autowired
	private StretchService stretchService;
	
	@Autowired
	private ProcessionService processionService;

	// Tests ----------------------------------------------------

	// Probamos a traernos los tramos disponibles para una hermandad
	@Test
	public void testFindAvailables() {
		Collection<Stretch> stretches;
		Procession procession;

		authenticate("brother1");
		
		// ID de la procesión 1
		procession = processionService.findOneIfPrincipal(45);
		
		stretches = stretchService.findAvailables(procession);
		
		unauthenticate();
		
		Assert.isTrue(stretches.size() == 2);
	}
}
