package services.positive;
 
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

import services.BoxInstanceService;
import utilities.AbstractTest;
import domain.BoxInstance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class BoxInstanceServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private BoxInstanceService boxInstanceService;

	// Tests -------------------------------------------------
	
	// Creamos un nuevo boxInstance
	@SuppressWarnings("deprecation")
	@Test
	public void testCreateAndSave() {
		BoxInstance boxInstance;
		Collection<BoxInstance> before;
		Collection<BoxInstance> after;

		authenticate("admin");

		before = boxInstanceService.findAll();
		
		//Id de la box1
		boxInstance = boxInstanceService.create(85);
		
		boxInstance.setDate(new Date(2016,12,12));
		
		boxInstanceService.save(boxInstance);
		
		after = boxInstanceService.findAll();

		Assert.isTrue(before.size() < after.size());

		authenticate(null);
	}
	
	// Editamos un boxInstance
	@SuppressWarnings("deprecation")
	@Test
	public void testEdit() {
		BoxInstance boxInstance;
		Date date;
		
		authenticate("admin");
		
		//Id de la boxInstance1
		boxInstance = boxInstanceService.findOne(88);
		
		date = boxInstance.getDate();
		
		boxInstance.setDate(new Date(2017,12,12));
		
		boxInstanceService.save(boxInstance);

		Assert.isTrue(!date.equals(boxInstance.getDate()));

		authenticate(null);
	}
	
	// Probamos a obtener las boxInstances a partir de una box
	@Test
	public void testFindByBox() {
		Collection<BoxInstance> boxInstances;
		
		authenticate("admin");
		
		//ID de la box 1
		boxInstances = boxInstanceService.findByBox(85);

		Assert.isTrue(boxInstances.size()==2);

		authenticate(null);
	}
	
	// Probamos a obtener las boxInstances a partir de una box
	@Test
	public void testFindAvailablesByBox() {
		Collection<BoxInstance> boxInstances;
		
		authenticate("admin");
		
		//ID de la box 1
		boxInstances = boxInstanceService.findAvailablesByBox(85);

		Assert.isTrue(boxInstances.size()==2);

		authenticate(null);
	}

}
