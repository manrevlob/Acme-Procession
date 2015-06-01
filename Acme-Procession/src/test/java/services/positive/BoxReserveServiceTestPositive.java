package services.positive;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.BoxInstanceService;
import services.BoxReserveService;
import services.BoxService;
import utilities.AbstractTest;
import domain.Box;
import domain.BoxInstance;
import domain.BoxReserve;
import forms.CreateBoxReserveForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class BoxReserveServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private BoxReserveService boxReserveService;
	@Autowired
	private BoxService boxService;
	@Autowired
	private BoxInstanceService boxInstanceService;

	// Tests -------------------------------------------------
	
	// Probamos a crear un boxReserve
	@Test
	public void testCreateAndSave() {
		BoxReserve boxReserve;
		Collection<BoxReserve> before;
		Collection<BoxReserve> after;
		Box box;
		List<BoxInstance> boxInstances;
		CreateBoxReserveForm createBoxReserveForm;

		authenticate("viewer1");

		before = boxReserveService.findByPrincipal();
		
		//ID de la box1
		box = boxService.findOne(85);
		boxInstances = (List<BoxInstance>) boxInstanceService.findAvailablesByBox(85);
		
		createBoxReserveForm = new CreateBoxReserveForm();
		createBoxReserveForm.setBox(box);
		createBoxReserveForm.setBoxInstance(boxInstances.get(0));
		createBoxReserveForm.setChairs(1);

		boxReserve = boxReserveService.create(createBoxReserveForm);
		
		boxReserveService.save(boxReserve);
		
		after = boxReserveService.findByPrincipal();

		Assert.isTrue(before.size() < after.size());

		authenticate(null);
	}
	
	// Probamos a cancelar un boxReserve
	@Test
	public void testCancelBoxReserve() {
		BoxReserve boxReserve;
		BoxReserve boxReserveAfter;

		authenticate("viewer1");
		
		boxReserve = boxReserveService.findOne(93);
		
		boxReserveService.cancel(boxReserve);

		boxReserveAfter = boxReserveService.findOne(93);
		
		Assert.isTrue(boxReserveAfter.getIsCancelled()==true);

		authenticate(null);
	}
	
	// Probamos a obtener un reserve a partir de invoice
	@Test
	public void testFindByBoxInvoice() {
		BoxReserve reserve;
		
		authenticate("viewer1");
		
		//ID de la boxInvoice 1
		reserve = boxReserveService.findByBoxInvoice(91);

		Assert.isTrue(reserve.getBoxInstance().getId()==88);

		authenticate(null);
	}

}
