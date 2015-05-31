package services.positive;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ViewerService;
import utilities.AbstractTest;
import domain.Viewer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ViewerServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private ViewerService viewerService;

	// Tests -------------------------------------------------
	
	// Comprobamos que devuelve el usuario logueado
	@Test
	public void testFindByPrincipal() {
		Viewer viewer;
		// ID del author1
		int userAccountId = 6;

		authenticate("viewer1");

		viewer = viewerService.findByPrincipal();

		Assert.isTrue(viewer.getUserAccount().getId() == userAccountId);

		authenticate(null);
	}

}
