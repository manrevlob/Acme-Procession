package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.BrotherhoodService;
import services.CarvingService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Carving;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CarvingServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private CarvingService carvingService;
	@Autowired
	private BrotherhoodService brotherhoodService;

	// Tests -------------------------------------------------

	// Comprobamos que no podemos crear un carving en una hermandad de la cuál
	// no somos hermanos mayores
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative1() {
		Carving carving;
		Brotherhood brotherhood;

		authenticate("brother2");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);
		carving = carvingService.create(brotherhood);

		carving.setName("Test");
		carving.setDescription("Test");
		carving.setAuthor("Test");
		carving.setYear(1990);

		carving = carvingService.save(carving);

		Assert.notNull(carving);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un carving siendo viewers
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative2() {
		Carving carving;
		Brotherhood brotherhood;

		authenticate("viewer1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);
		carving = carvingService.create(brotherhood);

		carving.setName("Test");
		carving.setDescription("Test");
		carving.setAuthor("Test");
		carving.setYear(1990);

		carving = carvingService.save(carving);

		Assert.notNull(carving);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un carving siendo administradores
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative3() {
		Carving carving;
		Brotherhood brotherhood;

		authenticate("admin");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);
		carving = carvingService.create(brotherhood);

		carving.setName("Test");
		carving.setDescription("Test");
		carving.setAuthor("Test");
		carving.setYear(1990);

		carving = carvingService.save(carving);

		Assert.notNull(carving);

		unauthenticate();
	}

	// Comprobamos que no podemos crear un carving sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative4() {
		Carving carving;
		Brotherhood brotherhood;

		unauthenticate();

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);
		carving = carvingService.create(brotherhood);

		carving.setName("Test");
		carving.setDescription("Test");
		carving.setAuthor("Test");
		carving.setYear(1990);

		carving = carvingService.save(carving);

		Assert.notNull(carving);
	}

}
