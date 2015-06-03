package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.AssessmentService;
import services.ProcessionService;
import utilities.AbstractTest;
import domain.Assessment;
import domain.Procession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AssessmentServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private ProcessionService processionService;

	// Tests -------------------------------------------------

	// Comprobamos que no podemos crear un assessment con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave() {
		Assessment assessment;
		Procession procession;

		authenticate("brother1");

		// ID processsion 1
		procession = processionService.findOne(45);

		assessment = assessmentService.create();

		assessment.setProcession(procession);

		assessment.setValoration(8);
		assessment.setStreet("arrabal");

		assessmentService.save(assessment);

		authenticate(null);
	}

	// Comprobamos que no podemos crear un assessment con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave2() {
		Assessment assessment;
		Procession procession;

		authenticate("admin");

		// ID processsion 1
		procession = processionService.findOne(45);

		assessment = assessmentService.create();

		assessment.setProcession(procession);

		assessment.setValoration(8);
		assessment.setStreet("arrabal");

		assessmentService.save(assessment);

		authenticate(null);
	}

	// Comprobamos que no podemos crear un assessment sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave3() {
		Assessment assessment;
		Procession procession;

		authenticate(null);

		// ID processsion 1
		procession = processionService.findOne(45);

		assessment = assessmentService.create();

		assessment.setProcession(procession);

		assessment.setValoration(8);
		assessment.setStreet("arrabal");

		assessmentService.save(assessment);
	}

	// Comprobamos que no podemos crear un assessment con un ID de procession
	// incorrecto
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave4() {
		Assessment assessment;
		Procession procession;

		authenticate("brother1");

		// ID processsion incorrecto
		procession = processionService.findOne(44);

		assessment = assessmentService.create();

		assessment.setProcession(procession);

		assessment.setValoration(8);
		assessment.setStreet("arrabal");

		assessmentService.save(assessment);

		authenticate(null);
	}

	// Comprobamos que no podemos editar un assessment sino eres el propietario
	@Test(expected = IllegalArgumentException.class)
	public void testEdit() {
		Assessment assessment;

		authenticate("viewer2");

		// Id del assessment1 que pertenece al viewer1
		assessment = assessmentService.findOne(115);

		assessment.setValoration(5);

		assessmentService.save(assessment);

		authenticate(null);
	}

	// Comprobamos que no podemos obtener las evaluaciones si estas logueado
	// como admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByViewer() {

		authenticate("admin");

		assessmentService.findAllByViewer();

		authenticate(null);
	}

	// Comprobamos que no podemos obtener las evaluaciones si estas logueado
	// como brother
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByViewer2() {

		authenticate("brother1");

		assessmentService.findAllByViewer();

		authenticate(null);
	}

	// Comprobamos que no podemos obtener las evaluaciones sino estas logueado
	@Test(expected = IllegalArgumentException.class)
	public void testFindAllByViewer3() {

		authenticate(null);

		assessmentService.findAllByViewer();
	}

}
