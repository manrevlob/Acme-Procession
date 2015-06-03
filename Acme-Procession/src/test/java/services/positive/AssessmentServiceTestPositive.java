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

import services.AssessmentService;
import services.BoxInstanceService;
import services.ProcessionService;
import utilities.AbstractTest;
import domain.Assessment;
import domain.BoxInstance;
import domain.Procession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class AssessmentServiceTestPositive extends AbstractTest {

	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private ProcessionService processionService;

	// Tests -------------------------------------------------
	
	// Creamos un nuevo assessment
	@Test
	public void testCreateAndSave() {
		Assessment assessment;
		Collection<Assessment> before;
		Collection<Assessment> after;
		Procession procession;
		
		authenticate("viewer1");
		
		before = assessmentService.findAll();
		
		// ID processsion 1
		procession = processionService.findOne(45);
			
		assessment = assessmentService.create();
		
		assessment.setProcession(procession);
		
		assessment.setValoration(8);
		assessment.setStreet("arrabal");
		
		assessmentService.save(assessment);
		
		after = assessmentService.findAll();
		
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
