package services.positive;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

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
@TransactionConfiguration(defaultRollback = false)
public class FloatStretchServiceTestPositive extends AbstractTest {
	// Service under test ------------------------------------
	@Autowired
	private FloatStretchService floatStretchService;
	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private CarvingService carvingService;

	// Tests -------------------------------------------------

	// Obtenemos todos los mensajes de una carpeta
	@Test
	public void testCreateAndSave() {
		FloatStretch floatStretch;
		Brotherhood brotherhood;
		Collection<Carving> carvingsToAdd;

		authenticate("brother1");

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

		Assert.notNull(floatStretch);
	}

	// Borramos un tramo sin registros asociados
	@Test
	public void testDelete() {
		FloatStretch floatStretch;
		Brotherhood brotherhood;
		int before;
		int after;

		Collection<Carving> carvingsToAdd;

		authenticate("brother1");

		carvingsToAdd = new ArrayList<Carving>();

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);

		// Creamos un nuevo tramo para borrarlo luego
		floatStretch = floatStretchService.create(brotherhood);

		// ID del carving 1
		carvingsToAdd.add(carvingService.findOne(48));

		// ID del carving 3
		carvingsToAdd.add(carvingService.findOne(50));

		floatStretch.setName("Test");
		floatStretch.setDescription("Test");
		floatStretch.setCarvings(carvingsToAdd);

		floatStretch = floatStretchService.save(floatStretch);

		before = floatStretchService.findByBrotherhood(brotherhood).size();

		floatStretchService.delete(floatStretch);

		after = floatStretchService.findByBrotherhood(brotherhood).size();

		unauthenticate();

		Assert.isTrue(before > after);
	}

	// Nos traemos un stretch que nos pertenezca a una hermandad de la cuál
	// somos hermano mayor
	@Test
	public void testFindOneIfPrincipal() {
		FloatStretch floatStretch;

		authenticate("brother1");

		// ID del float stretch 1
		floatStretch = floatStretchService.findOneIfPrincipal(57);

		unauthenticate();

		Assert.notNull(floatStretch);
	}

	// Nos traemos los stretches de las hermandades de las cuales somos hermanos
	// mayores
	@Test
	public void testFindMines() {
		Collection<FloatStretch> floatStretches;

		authenticate("brother1");

		floatStretches = floatStretchService.findMines();

		unauthenticate();

		Assert.isTrue(floatStretches.size() == 3);
	}

	// Nos traemos los stretches de una hermandad en cocreto
	@Test
	public void testFindByBrotherhood() {
		Collection<FloatStretch> floatStretches;
		Brotherhood brotherhood;

		// ID de la hermandad 1
		brotherhood = brotherhoodService.findOne(33);

		floatStretches = floatStretchService.findByBrotherhood(brotherhood);

		Assert.isTrue(floatStretches.size() == 3);
	}
}
