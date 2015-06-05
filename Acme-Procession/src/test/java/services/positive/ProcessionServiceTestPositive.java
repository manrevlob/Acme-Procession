package services.positive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import services.OrdinaryStretchService;
import services.ProcessionService;
import services.StretchOrderService;
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
@TransactionConfiguration(defaultRollback = false)
public class ProcessionServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private ProcessionService processionService;
	@Autowired
	private StretchOrderService stretchOrderService;
	@Autowired
	private OrdinaryStretchService ordinaryStretchService;
	@Autowired
	private FloatStretchService floatStretchService;
	@Autowired
	private CarvingService carvingService;

	// Tests -------------------------------------------------

	// Creamos una nueva procesi�n
	@Test
	public void testCreateAndSave() {
		Procession procession;
		Brotherhood brotherhood;
		int before;
		int after;
		Money money;

		authenticate("brother1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		before = processionService.findByBrotherhood(brotherhood.getId())
				.size();

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

		after = processionService.findByBrotherhood(brotherhood.getId()).size();

		unauthenticate();

		Assert.isTrue(before < after);
	}

	// Borramos una procesi�n (que creamos previamente para que no tenga tramos
	// asociados)
	@Test
	public void testDelete() {
		Procession procession;
		Brotherhood brotherhood;
		int before;
		int middle;
		int after;
		Money money;

		authenticate("brother1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOne(33);

		before = processionService.findByBrotherhood(brotherhood.getId())
				.size();

		procession = processionService.create(brotherhood);

		money = new Money();

		money.setAmount(10.0);
		money.setCurrency("Euro");

		procession.setName("Test");
		procession.setStartMoment(new Date(System.currentTimeMillis() + 100000));
		procession.setEndMoment(new Date(System.currentTimeMillis() + 1000001001));
		procession.setLocality("Test");
		procession.setItinerary("Test");
		procession.setAssociatedCost(money);
		procession.setComments("Test");

		procession = processionService.save(procession);

		middle = processionService.findByBrotherhood(brotherhood.getId())
				.size();

		processionService.delete(procession);

		after = processionService.findByBrotherhood(brotherhood.getId()).size();

		unauthenticate();

		Assert.isTrue(before == after && middle > before && middle > after);
	}

	// Accedemos a las procesiones de una hermandad en concreto
	@Test
	public void testFindByBrotherhood() {
		Collection<Procession> processions;

		// ID de la brotherhood 1
		processions = processionService.findByBrotherhood(33);

		Assert.isTrue(processions.size() == 2);
	}

	// Accedemos a las procesiones de una hermandad de la cu�l somos hermanos
	// mayores
	@Test
	public void testFindOneIfPrincipal() {
		Procession procession;

		authenticate("brother1");

		// ID de la procession 1
		procession = processionService.findOneIfPrincipal(45);

		Assert.notNull(procession);
	}

	// A�adimos un stretch de tipo ordinario a una procesi�n
	@Test
	public void testAddStretch1() {
		Procession procession;
		OrdinaryStretch ordinaryStretch;
		AddStretchToProcessionForm addStretchToProcessionForm;
		int before;
		int after;

		authenticate("brother1");

		addStretchToProcessionForm = new AddStretchToProcessionForm();

		// ID de la procesi�n 2
		procession = processionService.findOneIfPrincipal(46);

		// Nos creamos un nuevo tramo de paso
		ordinaryStretch = ordinaryStretchService.create(procession
				.getBrotherhood());
		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		addStretchToProcessionForm.setProcession(procession);
		addStretchToProcessionForm.setStretch(ordinaryStretch);

		before = stretchOrderService.findByProcession(procession).size();

		processionService.addStretch(addStretchToProcessionForm);

		after = stretchOrderService.findByProcession(procession).size();

		unauthenticate();

		Assert.isTrue(after > before);
	}

	// A�adimos un stretch de tipo paso a una procesi�n
	@Test
	public void testAddStretch2() {
		Procession procession;
		FloatStretch floatStretch;
		Carving carving;
		Collection<Carving> carvings;
		AddStretchToProcessionForm addStretchToProcessionForm;
		int before;
		int after;

		authenticate("brother1");

		addStretchToProcessionForm = new AddStretchToProcessionForm();

		// ID de la procesi�n 2
		procession = processionService.findOneIfPrincipal(46);

		// ID del carving 3
		carving = carvingService.create(procession.getBrotherhood());
		carving.setName("Test");
		carving.setDescription("Test");
		carving.setAuthor("Test");
		carving.setYear(1990);
		carving.setComments("Test");
		
		carving = carvingService.save(carving);

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

		before = stretchOrderService.findByProcession(procession).size();

		processionService.addStretch(addStretchToProcessionForm);

		after = stretchOrderService.findByProcession(procession).size();

		unauthenticate();

		Assert.isTrue(after > before);
	}

}
