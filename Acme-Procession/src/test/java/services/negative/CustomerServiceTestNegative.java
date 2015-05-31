package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;
import services.BrotherService;
import services.CustomerService;
import services.ViewerService;
import utilities.AbstractTest;
import domain.Brother;
import domain.Viewer;
import forms.RegistrationForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CustomerServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ViewerService viewerService;
	@Autowired
	private BrotherService brotherService;

	// Repository --------------------------------------------
	@Autowired
	private CustomerRepository customerRepository;

	// Tests -------------------------------------------------
	
	// Comprobamos que no funciona poniendo un usuario ya creado anteriormente
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateAndSaveViewer() {
		Viewer viewer;
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();
		viewer = viewerService.create();

		registrationForm.setBrandName("Visa");
		registrationForm.setCVV(327);
		registrationForm.setExpirationMonth(5);
		registrationForm.setExpirationYear(2020);
		registrationForm.setHolderName("TestName");
		registrationForm.setNumber("4532423142663403");

		registrationForm.setName("testing");
		registrationForm.setSurname("testing");
		registrationForm.setEmail("testing@acme.com");
		registrationForm.setNationality("Spain");

		registrationForm.setUsername("viewer1");
		registrationForm.setPassword("viewer1");
		registrationForm.setSecondPassword("viewer1");

		customerService.checkPassword(registrationForm);

		viewer = (Viewer) customerService.convertToCustomer(viewer,
				registrationForm);
		viewerService.registerToTheSystem(viewer);
		customerRepository.flush();
	}
	
	// Comprobamos que no funciona utilizando una tarjeta de credito expirada
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveViewer2() {
		Viewer viewer;
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();
		viewer = viewerService.create();

		registrationForm.setBrandName("Visa");
		registrationForm.setCVV(327);
		registrationForm.setExpirationMonth(5);
		registrationForm.setExpirationYear(2014);
		registrationForm.setHolderName("TestName");
		registrationForm.setNumber("4532423142663403");

		registrationForm.setName("testing");
		registrationForm.setSurname("testing");
		registrationForm.setEmail("testing@acme.com");
		registrationForm.setNationality("Spain");

		registrationForm.setUsername("viewer4");
		registrationForm.setPassword("viewer4");
		registrationForm.setSecondPassword("viewer4");

		customerService.checkPassword(registrationForm);

		viewer = (Viewer) customerService.convertToCustomer(viewer, registrationForm);
		
		viewerService.registerToTheSystem(viewer);
		
		customerRepository.flush();
	}
	
	// Comprobamos que no funciona utilizando dos passwords distintas
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveViewer3() {
		Viewer viewer;
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();
		viewer = viewerService.create();

		registrationForm.setBrandName("Visa");
		registrationForm.setCVV(327);
		registrationForm.setExpirationMonth(10);
		registrationForm.setExpirationYear(2015);
		registrationForm.setHolderName("TestName");
		registrationForm.setNumber("4532423142663403");

		registrationForm.setName("testing");
		registrationForm.setSurname("testing");
		registrationForm.setEmail("testing@acme.com");
		registrationForm.setNationality("Spain");


		registrationForm.setUsername("viewer4");
		registrationForm.setPassword("viewer");
		registrationForm.setSecondPassword("viewer4");

		customerService.checkPassword(registrationForm);

		viewer = (Viewer) customerService.convertToCustomer(viewer, registrationForm);
		
		viewerService.registerToTheSystem(viewer);
		
		customerRepository.flush();
	}

	// Comprobamos que no funciona poniendo un usuario ya creado anteriormente
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateAndSaveBrother() {
		Brother brother;
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();
		brother = brotherService.create();

		registrationForm.setBrandName("Visa");
		registrationForm.setCVV(327);
		registrationForm.setExpirationMonth(5);
		registrationForm.setExpirationYear(2020);
		registrationForm.setHolderName("TestName");
		registrationForm.setNumber("4532423142663403");

		registrationForm.setName("testing");
		registrationForm.setSurname("testing");
		registrationForm.setEmail("testing@acme.com");
		registrationForm.setNationality("Spain");

		registrationForm.setUsername("brother1");
		registrationForm.setPassword("brother1");
		registrationForm.setSecondPassword("brother1");

		customerService.checkPassword(registrationForm);

		brother = (Brother) customerService.convertToCustomer(brother,
				registrationForm);
		brotherService.registerToTheSystem(brother);
		customerRepository.flush();
	}
	
	// Comprobamos que no funciona utilizando una tarjeta de credito expirada
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveBrother2() {
		Brother brother;
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();
		brother = brotherService.create();

		registrationForm.setBrandName("Visa");
		registrationForm.setCVV(327);
		registrationForm.setExpirationMonth(5);
		registrationForm.setExpirationYear(2014);
		registrationForm.setHolderName("TestName");
		registrationForm.setNumber("4532423142663403");

		registrationForm.setName("testing");
		registrationForm.setSurname("testing");
		registrationForm.setEmail("testing@acme.com");
		registrationForm.setNationality("Spain");

		registrationForm.setUsername("brother4");
		registrationForm.setPassword("brother4");
		registrationForm.setSecondPassword("brother4");

		customerService.checkPassword(registrationForm);

		brother = (Brother) customerService.convertToCustomer(brother,
				registrationForm);
		brotherService.registerToTheSystem(brother);
		customerRepository.flush();
	}
	
	// Comprobamos que no funciona utilizando utilizando dos passwords distintas
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveBrother3() {
		Brother brother;
		RegistrationForm registrationForm;

		registrationForm = new RegistrationForm();
		brother = brotherService.create();

		registrationForm.setBrandName("Visa");
		registrationForm.setCVV(327);
		registrationForm.setExpirationMonth(5);
		registrationForm.setExpirationYear(2020);
		registrationForm.setHolderName("TestName");
		registrationForm.setNumber("4532423142663403");

		registrationForm.setName("testing");
		registrationForm.setSurname("testing");
		registrationForm.setEmail("testing@acme.com");
		registrationForm.setNationality("Spain");

		registrationForm.setUsername("brother4");
		registrationForm.setPassword("brother");
		registrationForm.setSecondPassword("brother4");

		customerService.checkPassword(registrationForm);

		brother = (Brother) customerService.convertToCustomer(brother,
				registrationForm);
		brotherService.registerToTheSystem(brother);
		customerRepository.flush();
	}
	

	// Probamos que no devuelve usuario sino hay un usuario logueado
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal() {
		
		unauthenticate();

		customerService.findByPrincipal();
	}

}
