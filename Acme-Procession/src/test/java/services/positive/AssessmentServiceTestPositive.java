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

import services.AssessmentService;
import services.ProcessionService;
import utilities.AbstractTest;
import domain.Assessment;
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
	
	// Editamos un assessment
	@Test
	public void testEdit() {
		Assessment assessment;
		Integer integer;
		
		authenticate("viewer1");
		
		//Id del assessment1
		assessment = assessmentService.findOne(115);
		
		integer = assessment.getValoration();
		
		assessment.setValoration(5);
		
		assessmentService.save(assessment);

		Assert.isTrue(!integer.equals(assessment.getValoration()));

		authenticate(null);
	}
	
	// Obtenemos todas las evaluaciones dado un viewer
	@Test
	public void testFindAllByViewer() {
		Collection<Assessment> assessments;
		
		authenticate("viewer1");
		
		assessments = assessmentService.findAllByViewer();

		// ID del assessment1 que pertenece al viewer1
		Assert.isTrue(assessments.contains(assessmentService.findOne(115)));

		authenticate(null);
	}
	
}
