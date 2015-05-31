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

import services.BrotherService;
import services.CustomerService;
import services.ViewerService;
import utilities.AbstractTest;
import domain.Brother;
import domain.Customer;
import domain.Viewer;
import forms.RegistrationForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CustomerServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ViewerService viewerService;
	@Autowired
	private BrotherService brotherService;

	// Tests -------------------------------------------------
	
	// Probamos a registrar un nuevo viewer

	@Test
	public void testCreateAndSaveViewer() {
		Viewer viewer;
		RegistrationForm registrationForm;
		Collection<Viewer> before;
		Collection<Viewer> after;

		authenticate(null);
		
		before = viewerService.findAll();

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

		registrationForm.setUsername("viewer11");
		registrationForm.setPassword("viewer11");
		registrationForm.setSecondPassword("viewer11");

		customerService.checkPassword(registrationForm);

		viewer = (Viewer) customerService.convertToCustomer(viewer,
				registrationForm);
		viewerService.registerToTheSystem(viewer);

		after = viewerService.findAll();

		Assert.isTrue(before.size() < after.size());

	}

	//Probamos a registrar un nuevo brother
	@Test
	public void testCreateAndSaveBrother() {
		Brother brother;
		RegistrationForm registrationForm;
		Collection<Brother> before;
		Collection<Brother> after;
		
		authenticate(null);

		before = brotherService.findAll();

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

		registrationForm.setUsername("brother11");
		registrationForm.setPassword("brother11");
		registrationForm.setSecondPassword("brother11");

		customerService.checkPassword(registrationForm);

		brother = (Brother) customerService.convertToCustomer(brother,
				registrationForm);
		brotherService.registerToTheSystem(brother);

		after = brotherService.findAll();

		Assert.isTrue(before.size() < after.size());

	}
	
	//Comprobamos que devuelve el usuario logueado siendo viewer
	@Test
	public void testFindByPrincipal() {
		Customer customer;

		authenticate("viewer1");

		customer = customerService.findByPrincipal();

		Assert.notNull(customer);

		authenticate(null);
	}
	
	//Comprobamos que devuelve el usuario logueado siendo brother
	@Test
	public void testFindByPrincipal2() {
		Customer customer;

		authenticate("brother1");

		customer = customerService.findByPrincipal();

		Assert.notNull(customer);

		authenticate(null);
	}

}
