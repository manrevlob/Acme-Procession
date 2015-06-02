package services.positive;

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
import services.OrdinaryStretchService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.OrdinaryStretch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class OrdinaryStretchServiceTestPositive extends AbstractTest {
	// Service under test ------------------------------------
	@Autowired
	private OrdinaryStretchService ordinaryStretchService;
	@Autowired
	private BrotherhoodService brotherhoodService;

	// Tests -------------------------------------------------

	// Obtenemos todos los mensajes de una carpeta
	@Test
	public void testCreateAndSave() {
		OrdinaryStretch ordinaryStretch;
		Brotherhood brotherhood;

		authenticate("brother1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		unauthenticate();

		Assert.notNull(ordinaryStretch);
	}

	// Borramos un tramo sin registros asociados
	@Test
	public void testDelete() {
		OrdinaryStretch ordinaryStretch;
		Brotherhood brotherhood;
		int before;
		int after;

		authenticate("brother1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);

		// Creamos un nuevo tramo para borrarlo luego
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		before = ordinaryStretchService.findByBrotherhood(brotherhood).size();

		ordinaryStretchService.delete(ordinaryStretch);

		after = ordinaryStretchService.findByBrotherhood(brotherhood).size();

		unauthenticate();

		Assert.isTrue(before > after);
	}

	// Nos traemos un stretch que nos pertenezca a una hermandad de la cuál
	// somos hermano mayor
	@Test
	public void testFindOneIfPrincipal() {
		OrdinaryStretch ordinaryStretch;

		authenticate("brother1");

		ordinaryStretch = ordinaryStretchService.findOneIfPrincipal(53);

		unauthenticate();

		Assert.notNull(ordinaryStretch);
	}

	// Nos traemos los stretches de las hermandades de las cuales somos hermanos
	// mayores
	@Test
	public void testFindMines() {
		Collection<OrdinaryStretch> ordinaryStretches;

		authenticate("brother1");

		ordinaryStretches = ordinaryStretchService.findMines();

		unauthenticate();

		Assert.isTrue(ordinaryStretches.size() == 2);
	}

	// Nos traemos los stretches de una hermandad en cocreto
	@Test
	public void testFindByBrotherhood() {
		Collection<OrdinaryStretch> ordinaryStretches;
		Brotherhood brotherhood;

		// ID de la hermandad 1
		brotherhood = brotherhoodService.findOne(33);

		ordinaryStretches = ordinaryStretchService
				.findByBrotherhood(brotherhood);

		Assert.isTrue(ordinaryStretches.size() == 2);
	}

}
