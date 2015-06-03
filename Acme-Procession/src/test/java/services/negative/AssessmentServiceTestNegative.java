package services.negative;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.AssessmentService;
import utilities.AbstractTest;
import domain.Assessment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AssessmentServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private AssessmentService assessmentService;

	// Tests -------------------------------------------------
	
	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave() {
		Assessment assessment;

		authenticate("brother1");
			
		assessment = assessmentService.create();
		
		assessment.setValoration(8);
		assessment.setStreet("arrabal");
		
		assessmentService.save(assessment);

		authenticate(null);
	}

	// Comprobamos que no funciona con el rol de viewer
	@SuppressWarnings("deprecation")
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave2() {
		Assessment boxInstance;

		authenticate("viewer1");
		
		//Id de la box1
		boxInstance = assessmentService.create(85);
		
		boxInstance.setDate(new Date(2016,12,12));
		
		assessmentService.save(boxInstance);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar autenticados 
	@SuppressWarnings("deprecation")
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave3() {
		Assessment boxInstance;

		authenticate(null);
		
		//Id de la box1
		boxInstance = assessmentService.create(85);
		
		boxInstance.setDate(new Date(2016,12,12));
		
		assessmentService.save(boxInstance);
	}
	
	// Comprobamos que no funciona si ponemos la fecha en pasado
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave4() {
		Assessment boxInstance;

		authenticate("admin");
		
		//Id de la box1
		boxInstance = assessmentService.create(85);
		
		// esta fecha será anterior a la utilizada para la comprobación del metodo
		boxInstance.setDate(new Date());
		
		assessmentService.save(boxInstance);

		authenticate(null);
	}
		
	// Comprobamos que no podemos editar siendo viewer
	@SuppressWarnings("deprecation")
	@Test(expected = IllegalArgumentException.class)
	public void testEditBoxInstance() {
		Assessment boxInstance;
		
		authenticate("viewer1");
		
		//Id de la boxInstance3
		boxInstance = assessmentService.findOne(90);
		
		boxInstance.setDate(new Date(2017,12,12));
		
		assessmentService.saveBoxInstance(boxInstance);

		authenticate(null);
	}

	// Comprobamos que no podemos editar siendo brother
	@SuppressWarnings("deprecation")
	@Test(expected = IllegalArgumentException.class)
	public void testEditBoxInstance2() {
		Assessment boxInstance;
		
		authenticate("brother1");
		
		//Id de la boxInstance3
		boxInstance = assessmentService.findOne(90);
		
		boxInstance.setDate(new Date(2017,12,12));
		
		assessmentService.saveBoxInstance(boxInstance);

		authenticate(null);
	}
	
	// Comprobamos que no podemos editar sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	@SuppressWarnings("deprecation")
	public void testEditBoxInstance3() {
		Assessment boxInstance;
		
		authenticate(null);
		
		//Id de la boxInstance3
		boxInstance = assessmentService.findOne(90);
		
		boxInstance.setDate(new Date(2017,12,12));
		
		assessmentService.saveBoxInstance(boxInstance);
	}
	
	// Comprobamos que no podemos utilizar una fecha anterior a la actual
	@Test(expected = IllegalArgumentException.class)
	public void testEditBoxInstance4() {
		Assessment boxInstance;
		
		authenticate("admin");
		
		//Id de la boxInstance3
		boxInstance = assessmentService.findOne(90);
		
		boxInstance.setDate(new Date());
		
		assessmentService.saveBoxInstance(boxInstance);

		authenticate(null);
	}
	
	// Comprobamos que no podemos editar si ya existen boxReserves asociadas
	@Test(expected = IllegalArgumentException.class)
	public void testEditBoxInstance5() {
		Assessment boxInstance;
		
		authenticate("admin");
		
		//Id de la boxInstance1 que tiene boxReserves asociadas
		boxInstance = assessmentService.findOne(88);
		
		boxInstance.setDate(new Date());
		
		assessmentService.saveBoxInstance(boxInstance);

		authenticate(null);
	}
	
	// Comprobamos que no funciona siendo brother
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBox() {
		
		authenticate("brother1");
		
		//ID de la box 1
		assessmentService.findByBox(85);

		authenticate(null);
	}
	
	// Comprobamos que no funciona siendo viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBox2() {
		
		authenticate("viewer1");
		
		//ID de la box 1
		assessmentService.findByBox(85);

		authenticate(null);
	}
	
	// Comprobamos que no funciona  sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBox3() {
		
		authenticate(null);
		
		//ID de la box 1
		assessmentService.findByBox(85);
	}
	
	// Comprobamos que no funciona siendo brother
	@Test(expected = IllegalArgumentException.class)
	public void testFindAvailablesByBox() {
		
		authenticate("brother1");
		
		//ID de la box 1
		assessmentService.findAvailablesByBox(85);

		authenticate(null);
	}
			
	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindAvailablesByBox2() {
		
		authenticate(null);
		
		//ID de la box 1
		assessmentService.findAvailablesByBox(85);
	}

}
