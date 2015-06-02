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

import services.BrotherhoodService;
import services.CarvingService;
import services.FloatStretchService;
import services.ProcessionService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Carving;
import domain.FloatStretch;
import domain.Procession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CarvingServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private CarvingService carvingService;
	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private ProcessionService processionService;
	@Autowired
	private FloatStretchService floatStretchService;

	// Tests -------------------------------------------------

	// Creamos y guardamos un nuevo carving
	@Test
	public void testCreateAndSave() {
		Carving carving;
		Brotherhood brotherhood;

		authenticate("brother1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		carving = carvingService.create(brotherhood);

		carving.setName("Test");
		carving.setDescription("Test");
		carving.setAuthor("Test");
		carving.setYear(1990);

		carving = carvingService.save(carving);

		Assert.notNull(carving);

		unauthenticate();
	}

	// Buscamos carvings por hermandad
	@Test
	public void testFindByBrotherhood() {
		Collection<Carving> carvings;

		// ID de la brotherhood 1
		carvings = carvingService.findByBrotherhood(33);

		Assert.isTrue(carvings.size() == 3);

		// ID de la brotherhood 2
		carvings = carvingService.findByBrotherhood(34);

		Assert.isTrue(carvings.size() == 2);
	}

	// Buscamos carvings por procesión
	@Test
	public void testFindByProcession() {
		Collection<Carving> carvings;
		Procession procession;

		// ID de la procession 1
		procession = processionService.findOne(45);

		carvings = carvingService.findByProcession(procession);

		Assert.isTrue(carvings.size() == 2);
	}

	// Buscamos carvings por tramo de paso
	@Test
	public void testFindByStretch() {
		Collection<Carving> carvings;
		FloatStretch floatStretch;

		// ID del float stretch 1
		floatStretch = floatStretchService.findOne(57);

		carvings = carvingService.findByStretch(floatStretch);

		Assert.isTrue(carvings.size() == 2);
	}

}
