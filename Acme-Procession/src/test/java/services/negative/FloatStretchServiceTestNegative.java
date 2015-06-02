package services.negative;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import services.BrotherhoodService;
import services.CarvingService;
import services.FloatStretchService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Carving;
import domain.FloatStretch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class FloatStretchServiceTestNegative extends AbstractTest {
	// Service under test ------------------------------------
	@Autowired
	private FloatStretchService floatStretchService;
	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private CarvingService carvingService;

	// Tests -------------------------------------------------

	// Intentamos crear un tramo en una hermandad en la cuál no somos hermano
	// mayor
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative1() {
		FloatStretch floatStretch;
		Brotherhood brotherhood;
		Collection<Carving> carvingsToAdd;

		authenticate("brother2");

		carvingsToAdd = new ArrayList<Carving>();

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		floatStretch = floatStretchService.create(brotherhood);

		// ID del carving 1
		carvingsToAdd.add(carvingService.findOne(48));

		// ID del carving 3
		carvingsToAdd.add(carvingService.findOne(50));

		floatStretch.setName("Test");
		floatStretch.setDescription("Test");
		floatStretch.setCarvings(carvingsToAdd);

		floatStretch = floatStretchService.save(floatStretch);

		unauthenticate();
	}

	// Intentamos crear un tramo en una hermandad como viewer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative2() {
		FloatStretch floatStretch;
		Brotherhood brotherhood;
		Collection<Carving> carvingsToAdd;

		authenticate("viewer1");

		carvingsToAdd = new ArrayList<Carving>();

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		floatStretch = floatStretchService.create(brotherhood);

		// ID del carving 1
		carvingsToAdd.add(carvingService.findOne(48));

		// ID del carving 3
		carvingsToAdd.add(carvingService.findOne(50));

		floatStretch.setName("Test");
		floatStretch.setDescription("Test");
		floatStretch.setCarvings(carvingsToAdd);

		floatStretch = floatStretchService.save(floatStretch);

		unauthenticate();
	}

	// Intentamos crear un tramo en una hermandad como administrador
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative3() {
		FloatStretch floatStretch;
		Brotherhood brotherhood;
		Collection<Carving> carvingsToAdd;

		authenticate("admin");

		carvingsToAdd = new ArrayList<Carving>();

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		floatStretch = floatStretchService.create(brotherhood);

		// ID del carving 1
		carvingsToAdd.add(carvingService.findOne(48));

		// ID del carving 3
		carvingsToAdd.add(carvingService.findOne(50));

		floatStretch.setName("Test");
		floatStretch.setDescription("Test");
		floatStretch.setCarvings(carvingsToAdd);

		floatStretch = floatStretchService.save(floatStretch);

		unauthenticate();
	}

	// Intentamos crear un tramo en una hermandad sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative4() {
		FloatStretch floatStretch;
		Brotherhood brotherhood;
		Collection<Carving> carvingsToAdd;

		unauthenticate();

		carvingsToAdd = new ArrayList<Carving>();

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		floatStretch = floatStretchService.create(brotherhood);

		// ID del carving 1
		carvingsToAdd.add(carvingService.findOne(48));

		// ID del carving 3
		carvingsToAdd.add(carvingService.findOne(50));

		floatStretch.setName("Test");
		floatStretch.setDescription("Test");
		floatStretch.setCarvings(carvingsToAdd);

		floatStretch = floatStretchService.save(floatStretch);
	}

	// Intentamos borrar un tramo CON registros asociados
	@Test(expected = IllegalArgumentException.class)
	public void testDelete() {
		FloatStretch floatStretch;

		authenticate("brother1");

		// ID del tramo de paso 1
		floatStretch = floatStretchService.findOneIfPrincipal(57);

		floatStretchService.delete(floatStretch);

		unauthenticate();
	}

	// Nos traemos un stretch que nos pertenezca a una hermandad de la cuál
	// NO somos hermano mayor
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal() {
		authenticate("brother2");

		// ID del float stretch 1
		floatStretchService.findOneIfPrincipal(57);

		unauthenticate();
	}

	// Nos traemos los stretches de las hermandades de las cuales somos hermanos
	// mayores SIN ser hermano mayor de ninguna hermandad
	@Test(expected = IllegalArgumentException.class)
	public void testFindMines() {

		authenticate("brother5");

		floatStretchService.findMines();

		unauthenticate();
	}
}
