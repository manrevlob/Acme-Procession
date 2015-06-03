package services.negative;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Brotherhood;
import domain.OrdinaryStretch;
import domain.Procession;
import domain.StretchOrder;

import services.BrotherhoodService;
import services.OrdinaryStretchService;
import services.ProcessionService;
import services.StretchOrderService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class StretchOrderServiceTestNegative extends AbstractTest {
	// Service under test ------------------------------------
	@Autowired
	private StretchOrderService stretchOrderService;
	@Autowired
	private ProcessionService processionService;
	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private OrdinaryStretchService ordinaryStretchService;

	// Tests -------------------------------------------------

	// Intentamos crear una stretchOrder en una procesión en la que no tenemos
	// permisos
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative1() {
		Brotherhood brotherhood;
		Procession procession;
		StretchOrder stretchOrder;
		OrdinaryStretch ordinaryStretch;

		authenticate("brother2");

		// Id de la brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);

		// Id de la procession 2
		procession = processionService.findOneIfPrincipal(46);

		// Creamos un nuevo tramo ordinario para añadirlo a continuación
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setBrotherhood(brotherhood);
		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		stretchOrder = stretchOrderService.createByStretchAndProcession(
				ordinaryStretch, procession);
		stretchOrder = stretchOrderService.save(stretchOrder);

		authenticate(null);
	}

	// Intentamos crear una stretchOrder en una procesión siendo viewers
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative2() {
		Brotherhood brotherhood;
		Procession procession;
		StretchOrder stretchOrder;
		OrdinaryStretch ordinaryStretch;

		authenticate("viewer1");

		// Id de la brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);

		// Id de la procession 2
		procession = processionService.findOneIfPrincipal(46);

		// Creamos un nuevo tramo ordinario para añadirlo a continuación
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setBrotherhood(brotherhood);
		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		stretchOrder = stretchOrderService.createByStretchAndProcession(
				ordinaryStretch, procession);
		stretchOrder = stretchOrderService.save(stretchOrder);

		authenticate(null);
	}

	// Intentamos crear una stretchOrder en una procesión siendo administrador
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative3() {
		Brotherhood brotherhood;
		Procession procession;
		StretchOrder stretchOrder;
		OrdinaryStretch ordinaryStretch;

		authenticate("admin");

		// Id de la brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);

		// Id de la procession 2
		procession = processionService.findOneIfPrincipal(46);

		// Creamos un nuevo tramo ordinario para añadirlo a continuación
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setBrotherhood(brotherhood);
		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		stretchOrder = stretchOrderService.createByStretchAndProcession(
				ordinaryStretch, procession);
		stretchOrder = stretchOrderService.save(stretchOrder);

		authenticate(null);
	}

	// Intentamos crear una stretchOrder en una procesión sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative4() {
		Brotherhood brotherhood;
		Procession procession;
		StretchOrder stretchOrder;
		OrdinaryStretch ordinaryStretch;

		unauthenticate();

		// Id de la brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);

		// Id de la procession 2
		procession = processionService.findOneIfPrincipal(46);

		// Creamos un nuevo tramo ordinario para añadirlo a continuación
		ordinaryStretch = ordinaryStretchService.create(brotherhood);

		ordinaryStretch.setBrotherhood(brotherhood);
		ordinaryStretch.setName("Test");
		ordinaryStretch.setDescription("Test");
		ordinaryStretch.setMaxNumberOfBrothers(100);

		ordinaryStretch = ordinaryStretchService.save(ordinaryStretch);

		stretchOrder = stretchOrderService.createByStretchAndProcession(
				ordinaryStretch, procession);
		stretchOrder = stretchOrderService.save(stretchOrder);
	}

	// Intentamos mover una stretchOrder hacia una posicion anterior de la
	// procesión SIENDO YA LA PRIMERA
	@Test(expected = IllegalArgumentException.class)
	public void testMoveToUp() {
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;
		int before;
		int after;

		authenticate("brother1");

		// Id del stretchOrder 4
		stretchOrder = stretchOrderService.findOne(64);
		stretchOrders = stretchOrderService.findByProcession(stretchOrder
				.getProcession());

		before = stretchOrder.getOrderNumber();

		stretchOrderService.moveToUp(stretchOrder, stretchOrders);

		after = stretchOrder.getOrderNumber();

		Assert.isTrue(before > after);

		authenticate(null);
	}

	// Intentamos mover una stretchOrder hacia una posicion posterior de la
	// procesión SIENDO YA LA ÚLTIMA
	@Test(expected = IllegalArgumentException.class)
	public void testMoveToDown() {
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;
		int before;
		int after;

		authenticate("brother1");

		// ID del stretchOrder 7
		stretchOrder = stretchOrderService.findOne(67);
		stretchOrders = stretchOrderService.findByProcession(stretchOrder
				.getProcession());

		before = stretchOrder.getOrderNumber();

		stretchOrderService.moveToDown(stretchOrder, stretchOrders);

		after = stretchOrder.getOrderNumber();

		Assert.isTrue(before < after);

		authenticate(null);
	}

	// Intentamos eliminar una stretchOrder de la procesión teniendo ya
	// registros asociados
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteAndReorderNegative() {
		StretchOrder stretchOrder;
		Collection<StretchOrder> before;
		Collection<StretchOrder> after;

		authenticate("brother1");

		// ID del stretchOrder 4
		stretchOrder = stretchOrderService.findOne(64);

		before = stretchOrderService.findByProcession(stretchOrder
				.getProcession());

		stretchOrderService.deleteAndReorder(stretchOrder, before);

		after = stretchOrderService.findByProcession(stretchOrder
				.getProcession());

		Assert.isTrue(before.size() > after.size());

		authenticate(null);
	}
}
