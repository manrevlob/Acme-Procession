package services.negative;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import services.OrdinaryStretchService;
import services.ProcessionService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Carving;
import domain.FloatStretch;
import domain.Money;
import domain.OrdinaryStretch;
import domain.Procession;
import forms.AddStretchToProcessionForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ProcessionServiceTestNegative extends AbstractTest {
	// Service under test ------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private ProcessionService processionService;
	@Autowired
	private OrdinaryStretchService ordinaryStretchService;
	@Autowired
	private FloatStretchService floatStretchService;
	@Autowired
	private CarvingService carvingService;

	// Tests -------------------------------------------------

	// Intentamos crear una procesión si ser big brother de la misma
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative1() {
		Procession procession;
		Brotherhood brotherhood;
		Money money;

		authenticate("brother2");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		procession = processionService.create(brotherhood);

		money = new Money();

		money.setAmount(10.0);
		money.setCurrency("Euro");

		procession.setName("Test");
		procession.setStartMoment(new Date(System.currentTimeMillis() + 1));
		procession.setEndMoment(new Date(System.currentTimeMillis() + 1001));
		procession.setLocality("Test");
		procession.setItinerary("Test");
		procession.setAssociatedCost(money);
		procession.setComments("Test");

		processionService.save(procession);

		unauthenticate();
	}

	// Intentamos crear una procesión siendo viewer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative2() {
		Procession procession;
		Brotherhood brotherhood;
		Money money;

		authenticate("viewer1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		procession = processionService.create(brotherhood);

		money = new Money();

		money.setAmount(10.0);
		money.setCurrency("Euro");

		procession.setName("Test");
		procession.setStartMoment(new Date(System.currentTimeMillis() + 1));
		procession.setEndMoment(new Date(System.currentTimeMillis() + 1001));
		procession.setLocality("Test");
		procession.setItinerary("Test");
		procession.setAssociatedCost(money);
		procession.setComments("Test");

		processionService.save(procession);

		unauthenticate();
	}

	// Intentamos crear una procesión siendo administradores
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative3() {
		Procession procession;
		Brotherhood brotherhood;
		Money money;

		authenticate("admin");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		procession = processionService.create(brotherhood);

		money = new Money();

		money.setAmount(10.0);
		money.setCurrency("Euro");

		procession.setName("Test");
		procession.setStartMoment(new Date(System.currentTimeMillis() + 1));
		procession.setEndMoment(new Date(System.currentTimeMillis() + 1001));
		procession.setLocality("Test");
		procession.setItinerary("Test");
		procession.setAssociatedCost(money);
		procession.setComments("Test");

		processionService.save(procession);

		unauthenticate();
	}

	// Intentamos crear una procesión sin estar logeados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative4() {
		Procession procession;
		Brotherhood brotherhood;
		Money money;

		unauthenticate();

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		procession = processionService.create(brotherhood);

		money = new Money();

		money.setAmount(10.0);
		money.setCurrency("Euro");

		procession.setName("Test");
		procession.setStartMoment(new Date(System.currentTimeMillis() + 1));
		procession.setEndMoment(new Date(System.currentTimeMillis() + 1001));
		procession.setLocality("Test");
		procession.setItinerary("Test");
		procession.setAssociatedCost(money);
		procession.setComments("Test");

		processionService.save(procession);
	}

	// Intentamos borrar una procesión sin ser hermanos mayores
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNegative1() {
		Procession procession;
		Brotherhood brotherhood;
		Money money;

		authenticate("brother2");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		procession = processionService.create(brotherhood);

		money = new Money();

		money.setAmount(10.0);
		money.setCurrency("Euro");

		procession.setName("Test");
		procession.setStartMoment(new Date(System.currentTimeMillis() + 1));
		procession.setEndMoment(new Date(System.currentTimeMillis() + 1001));
		procession.setLocality("Test");
		procession.setItinerary("Test");
		procession.setAssociatedCost(money);
		procession.setComments("Test");

		procession = processionService.save(procession);

		processionService.delete(procession);

		unauthenticate();
	}

	// Intentamos borrar una procesión siendo viewers
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNegative2() {
		Procession procession;
		Brotherhood brotherhood;
		Money money;

		authenticate("viewer1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		procession = processionService.create(brotherhood);

		money = new Money();

		money.setAmount(10.0);
		money.setCurrency("Euro");

		procession.setName("Test");
		procession.setStartMoment(new Date(System.currentTimeMillis() + 1));
		procession.setEndMoment(new Date(System.currentTimeMillis() + 1001));
		procession.setLocality("Test");
		procession.setItinerary("Test");
		procession.setAssociatedCost(money);
		procession.setComments("Test");

		procession = processionService.save(procession);

		processionService.delete(procession);

		unauthenticate();
	}

	// Intentamos borrar una procesión siendo administradores
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNegative3() {
		Procession procession;
		Brotherhood brotherhood;
		Money money;

		authenticate("admin");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		procession = processionService.create(brotherhood);

		money = new Money();

		money.setAmount(10.0);
		money.setCurrency("Euro");

		procession.setName("Test");
		procession.setStartMoment(new Date(System.currentTimeMillis() + 1));
		procession.setEndMoment(new Date(System.currentTimeMillis() + 1001));
		procession.setLocality("Test");
		procession.setItinerary("Test");
		procession.setAssociatedCost(money);
		procession.setComments("Test");

		procession = processionService.save(procession);

		processionService.delete(procession);

		unauthenticate();
	}

	// Intentamos borrar una procesión sin estar logeados
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNegative4() {
		Procession procession;
		Brotherhood brotherhood;
		Money money;

		unauthenticate();

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		procession = processionService.create(brotherhood);

		money = new Money();

		money.setAmount(10.0);
		money.setCurrency("Euro");

		procession.setName("Test");
		procession.setStartMoment(new Date(System.currentTimeMillis() + 1));
		procession.setEndMoment(new Date(System.currentTimeMillis() + 1001));
		procession.setLocality("Test");
		procession.setItinerary("Test");
		procession.setAssociatedCost(money);
		procession.setComments("Test");

		procession = processionService.save(procession);

		processionService.delete(procession);
	}

	// Accedemos a las procesiones de una hermandad de la cuál NO somos hermanos
	// mayores
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal() {
		Procession procession;

		authenticate("brother2");

		// ID de la procession 1
		procession = processionService.findOneIfPrincipal(45);

		Assert.notNull(procession);
	}

	// Intentamos añadir un stretch de tipo ordinario a una procesión sin tener
	// permisos
	@Test(expected = IllegalArgumentException.class)
	public void testAddStretchNegative1() {
		Procession procession;
		OrdinaryStretch ordinaryStretch;
		AddStretchToProcessionForm addStretchToProcessionForm;

		authenticate("brother2");

		addStretchToProcessionForm = new AddStretchToProcessionForm();

		// ID de la procesión 1
		procession = processionService.findOneIfPrincipal(45);

		// Nos creamos un nuevo tramo de paso
		ordinaryStretch = ordinaryStretchService.create(procession
				.getBrotherhood());
		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		addStretchToProcessionForm.setProcession(procession);
		addStretchToProcessionForm.setStretch(ordinaryStretch);

		processionService.addStretch(addStretchToProcessionForm);

		unauthenticate();
	}

	// Intentamos añadir un stretch de tipo paso a una procesión sin tener
	// permisos
	@Test(expected = IllegalArgumentException.class)
	public void testAddStretchNegative2() {
		Procession procession;
		FloatStretch floatStretch;
		Carving carving;
		Collection<Carving> carvings;
		AddStretchToProcessionForm addStretchToProcessionForm;

		authenticate("brother2");

		addStretchToProcessionForm = new AddStretchToProcessionForm();

		// ID de la procesión 1
		procession = processionService.findOneIfPrincipal(45);

		// ID del carving 3
		carving = carvingService.findOne(50);

		carvings = new ArrayList<Carving>();
		carvings.add(carving);

		// Nos creamos un nuevo tramo de paso
		floatStretch = floatStretchService.create(procession.getBrotherhood());
		floatStretch.setName("Test");
		floatStretch.setDescription("Test");
		floatStretch.setCarvings(carvings);

		floatStretch = floatStretchService.save(floatStretch);

		addStretchToProcessionForm.setProcession(procession);
		addStretchToProcessionForm.setStretch(floatStretch);

		processionService.addStretch(addStretchToProcessionForm);

		unauthenticate();
	}

	// Intentamos añadir un stretch de tipo paso a una procesión que ya contiene
	// uno de los carvings relacionados
	@Test(expected = IllegalArgumentException.class)
	public void testAddStretchNegative3() {
		Procession procession;
		FloatStretch floatStretch;
		AddStretchToProcessionForm addStretchToProcessionForm;

		authenticate("brother1");

		addStretchToProcessionForm = new AddStretchToProcessionForm();

		// ID de la procesión 1
		procession = processionService.findOneIfPrincipal(45);

		// ID del tramo de paso 2
		floatStretch = floatStretchService.findOne(58);

		addStretchToProcessionForm.setProcession(procession);
		addStretchToProcessionForm.setStretch(floatStretch);

		processionService.addStretch(addStretchToProcessionForm);

		unauthenticate();
	}
}
