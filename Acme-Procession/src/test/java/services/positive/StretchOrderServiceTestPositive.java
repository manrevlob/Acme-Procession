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
import services.OrdinaryStretchService;
import services.ProcessionService;
import services.StretchOrderService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.OrdinaryStretch;
import domain.Procession;
import domain.StretchOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class StretchOrderServiceTestPositive extends AbstractTest {

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

	// Creamos una stretchOrder
	@Test
	public void testCreateAndSave() {
		Brotherhood brotherhood;
		Procession procession;
		StretchOrder stretchOrder;
		OrdinaryStretch ordinaryStretch;
		Collection<StretchOrder> before;
		Collection<StretchOrder> after;

		authenticate("brother1");

		before = stretchOrderService.findAll();

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

		stretchOrder = stretchOrderService.createByStretchAndProcession(ordinaryStretch, procession);
		stretchOrder = stretchOrderService.save(stretchOrder);

		after = stretchOrderService.findAll();

		Assert.isTrue(before.size() < after.size());

		authenticate(null);
	}

	// Buscamos todas las stretchOrder de una procesión
	@Test
	public void testFindByBrotherhood() {
		Collection<StretchOrder> stretchOrders;
		Procession procession;

		// ID del brotherhood 1
		procession = processionService.findOne(46);

		stretchOrders = stretchOrderService.findByProcession(procession);

		Assert.isTrue(stretchOrders.size() == 4);
	}

	// Movemos una stretchOrder hacia una posicion anterior de la procesión
	@Test
	public void testMoveToUp() {
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;
		int before;
		int after;

		authenticate("brother1");

		// Id del stretchOrder 5
		stretchOrder = stretchOrderService.findOne(65);
		stretchOrders = stretchOrderService.findByProcession(stretchOrder.getProcession());
		
		before = stretchOrder.getOrderNumber();

		stretchOrderService.moveToUp(stretchOrder, stretchOrders);

		after = stretchOrder.getOrderNumber();

		Assert.isTrue(before > after);

		authenticate(null);
	}

	// Movemos una stretchOrder hacia una posicion posterior de la procesión
	@Test
	public void testMoveToDown() {
		StretchOrder stretchOrder;
		Collection<StretchOrder> stretchOrders;
		int before;
		int after;

		authenticate("brother1");

		// ID del stretchOrder 4
		stretchOrder = stretchOrderService.findOne(64);
		stretchOrders = stretchOrderService.findByProcession(stretchOrder.getProcession());

		before = stretchOrder.getOrderNumber();

		stretchOrderService.moveToDown(stretchOrder, stretchOrders);

		after = stretchOrder.getOrderNumber();

		Assert.isTrue(before < after);

		authenticate(null);
	}

	// Eliminamos una stretchOrder (sin registros todavía) de la procesión
	@Test
	public void testDeleteAndReorder() {
		StretchOrder stretchOrder;
		Collection<StretchOrder> before;
		Collection<StretchOrder> after;

		authenticate("brother1");

		// ID del stretchOrder 5
		stretchOrder = stretchOrderService.findOne(65);

		before = stretchOrderService.findByProcession(stretchOrder.getProcession());

		stretchOrderService.deleteAndReorder(stretchOrder, before);

		after = stretchOrderService.findByProcession(stretchOrder.getProcession());

		Assert.isTrue(before.size() > after.size());

		authenticate(null);
	}

}
