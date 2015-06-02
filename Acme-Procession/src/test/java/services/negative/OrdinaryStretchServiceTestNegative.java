package services.negative;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import services.BrotherhoodService;
import services.OrdinaryStretchService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.OrdinaryStretch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OrdinaryStretchServiceTestNegative extends AbstractTest {
	// Service under test ------------------------------------
	@Autowired
	private OrdinaryStretchService ordinaryStretchService;
	@Autowired
	private BrotherhoodService brotherhoodService;

	// Tests -------------------------------------------------

	// Intentamos crear un tramo en una hermandad en la cuál no somos hermano
	// mayor
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative1() {
		OrdinaryStretch ordinaryStretch;
		Brotherhood brotherhood;

		authenticate("brother2");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		unauthenticate();
	}

	// Intentamos crear un tramo en una hermandad como viewer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative2() {
		OrdinaryStretch ordinaryStretch;
		Brotherhood brotherhood;

		authenticate("viewer1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		unauthenticate();
	}

	// Intentamos crear un tramo en una hermandad como administrador
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative3() {
		OrdinaryStretch ordinaryStretch;
		Brotherhood brotherhood;

		authenticate("admin");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		unauthenticate();
	}

	// Intentamos crear un tramo en una hermandad sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative4() {
		OrdinaryStretch ordinaryStretch;
		Brotherhood brotherhood;

		unauthenticate();

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);
	}

	// Intentamos borrar un tramo CON registros asociados
	@Test(expected = IllegalArgumentException.class)
	public void testDelete() {
		OrdinaryStretch ordinaryStretch;

		authenticate("brother1");

		// ID del tramo ordinario 1
		ordinaryStretch = ordinaryStretchService.findOneIfPrincipal(53);

		ordinaryStretchService.delete(ordinaryStretch);

		unauthenticate();
	}

	// Nos traemos un stretch que nos pertenezca a una hermandad de la cuál
	// NO somos hermano mayor
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal() {
		authenticate("brother2");

		// ID del ordinary stretch 1
		ordinaryStretchService.findOneIfPrincipal(53);

		unauthenticate();
	}

	// Nos traemos los stretches de las hermandades de las cuales somos hermanos
	// mayores SIN ser hermano mayor de ninguna hermandad
	@Test(expected = IllegalArgumentException.class)
	public void testFindMines() {

		authenticate("brother5");

		ordinaryStretchService.findMines();

		unauthenticate();
	}
}
