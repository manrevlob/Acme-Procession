package services.negative;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.BoxInstanceService;
import services.BoxInvoiceService;
import services.BoxReserveService;
import services.BoxService;
import utilities.AbstractTest;
import domain.Box;
import domain.BoxInstance;
import domain.BoxInvoice;
import domain.BoxReserve;
import forms.CreateBoxReserveForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BoxReserveServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private BoxReserveService boxReserveService;
	@Autowired
	private BoxService boxService;
	@Autowired
	private BoxInstanceService boxInstanceService;
	@Autowired
	private BoxInvoiceService boxInvoiceService;
	
	// Tests -------------------------------------------------
	
	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave() {
		BoxReserve boxReserve;
		Box box;
		List<BoxInstance> boxInstances;
		CreateBoxReserveForm createBoxReserveForm;

		authenticate("brother1");
		
		//ID de la box1
		box = boxService.findOne(85);
		boxInstances = (List<BoxInstance>) boxInstanceService.findAvailablesByBox(85);
		
		createBoxReserveForm = new CreateBoxReserveForm();
		createBoxReserveForm.setBox(box);
		createBoxReserveForm.setBoxInstance(boxInstances.get(0));
		createBoxReserveForm.setChairs(1);

		boxReserve = boxReserveService.create(createBoxReserveForm);
		
		boxReserveService.save(boxReserve);

		authenticate(null);
	}

	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave2() {
		BoxReserve boxReserve;
		Box box;
		List<BoxInstance> boxInstances;
		CreateBoxReserveForm createBoxReserveForm;

		authenticate("admin");
		
		//ID de la box1
		box = boxService.findOne(85);
		boxInstances = (List<BoxInstance>) boxInstanceService.findAvailablesByBox(85);
		
		createBoxReserveForm = new CreateBoxReserveForm();
		createBoxReserveForm.setBox(box);
		createBoxReserveForm.setBoxInstance(boxInstances.get(0));
		createBoxReserveForm.setChairs(1);

		boxReserve = boxReserveService.create(createBoxReserveForm);
		
		boxReserveService.save(boxReserve);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar autenticados 
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave3() {
		BoxReserve boxReserve;
		Box box;
		List<BoxInstance> boxInstances;
		CreateBoxReserveForm createBoxReserveForm;

		authenticate(null);
		
		//ID de la box1
		box = boxService.findOne(85);
		boxInstances = (List<BoxInstance>) boxInstanceService.findAvailablesByBox(85);
		
		createBoxReserveForm = new CreateBoxReserveForm();
		createBoxReserveForm.setBox(box);
		createBoxReserveForm.setBoxInstance(boxInstances.get(0));
		createBoxReserveForm.setChairs(1);

		boxReserve = boxReserveService.create(createBoxReserveForm);
		
		boxReserveService.save(boxReserve);
	}
		
	// Comprobamos que no podemos cancelar sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testCancelBoxReserve() {
		BoxReserve boxReserve;

		authenticate(null);
		
		// ID de la reserva 1
		boxReserve = boxReserveService.findOne(93);
		
		boxReserveService.cancel(boxReserve);
	}

	// Comprobamos que no podemos cancelar siendo admin
	@Test(expected = IllegalArgumentException.class)
	public void testCancelBoxReserve2() {
		BoxReserve boxReserve;

		authenticate("admin");
		
		// ID de la reserva 1
		boxReserve = boxReserveService.findOne(93);
		
		boxReserveService.cancel(boxReserve);

		authenticate(null);
	}
	
	// Comprobamos que no podemos cancelar siendo brother
	@Test(expected = IllegalArgumentException.class)
	public void testCancelBoxReserve3() {
		BoxReserve boxReserve;

		authenticate("brother1");
		
		// ID de la reserva 1
		boxReserve = boxReserveService.findOne(93);
		
		boxReserveService.cancel(boxReserve);

		authenticate(null);
	}
	
	// Comprobamos que no podemos cancelar una reserva cancelada
	@Test(expected = IllegalArgumentException.class)
	public void testCancelBoxReserve4() {
		BoxReserve boxReserve;

		authenticate("viewer1");
		
		// ID de la reserva 1
		boxReserve = boxReserveService.findOne(93);
		
		boxReserveService.cancel(boxReserve);
		boxReserveService.cancel(boxReserve);

		authenticate(null);
	}
	
	// Comprobamos que no podemos cancelar una reserva con una invoice pagada
	@Test(expected = IllegalArgumentException.class)
	public void testCancelBoxReserve5() {
		BoxReserve boxReserve;
		BoxInvoice boxInvoice;

		authenticate("viewer1");
		
		// ID de la reserva 1
		boxReserve = boxReserveService.findOne(93);
		//ID de la boxInvoice 1 asociada a la reserva 1
		boxInvoice = boxInvoiceService.findOne(91);
		
		boxInvoiceService.payInvoice(boxInvoice);
		
		boxReserveService.cancel(boxReserve);

		authenticate(null);
	}
	
	// Comprobamos que no podemos cancelar una reserva que no es mia
	@Test(expected = IllegalArgumentException.class)
	public void testCancelBoxReserve6() {
		BoxReserve boxReserve;

		authenticate("viewer2");
		
		// ID de la reserva 1
		boxReserve = boxReserveService.findOne(93);
		
		boxReserveService.cancel(boxReserve);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBoxInvoice() {
		
		authenticate("brother1");
		
		//ID de la boxInvoice 1
		boxReserveService.findByBoxInvoice(91);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBoxInvoice2() {
		
		authenticate("admin");
		
		//ID de la boxInvoice 1
		boxReserveService.findByBoxInvoice(91);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBoxInvoice3() {
		
		authenticate(null);
		
		//ID de la boxInvoice 1
		boxReserveService.findByBoxInvoice(91);
	}

}
