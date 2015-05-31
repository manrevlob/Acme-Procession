package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import repositories.AdministratorRepository;
import services.AdministratorService;

import utilities.AbstractTest;
import domain.Administrator;
import forms.RegistrationAdminForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AdministratorServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	// Repository --------------------------------------------
	@Autowired
	private AdministratorRepository administratorRepository;
	
	// Tests -------------------------------------------------
	
	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave() {
		Administrator administrator;
		RegistrationAdminForm registrationAdminForm;

		authenticate("brother1");

		registrationAdminForm = new RegistrationAdminForm();
		administrator = administratorService.create();

		registrationAdminForm.setName("testing");
		registrationAdminForm.setSurname("testing");
		registrationAdminForm.setEmail("testing@acme.com");

		registrationAdminForm.setUsername("admin2");
		registrationAdminForm.setPassword("admin2");
		registrationAdminForm.setSecondPassword("admin2");
		
		administratorService.checkPassword(registrationAdminForm);

		administrator = administratorService.convertToAdministrator(
				administrator, registrationAdminForm);
		administratorService.registerToTheSystem(administrator);

		authenticate(null);
	}

	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave2() {
		Administrator administrator;
		RegistrationAdminForm registrationAdminForm;

		authenticate("viewer1");

		registrationAdminForm = new RegistrationAdminForm();
		administrator = administratorService.create();

		registrationAdminForm.setName("testing");
		registrationAdminForm.setSurname("testing");
		registrationAdminForm.setEmail("testing@acme.com");

		registrationAdminForm.setUsername("admin2");
		registrationAdminForm.setPassword("admin2");
		registrationAdminForm.setSecondPassword("admin2");
		
		administratorService.checkPassword(registrationAdminForm);

		administrator = administratorService.convertToAdministrator(
				administrator, registrationAdminForm);
		administratorService.registerToTheSystem(administrator);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar autenticados 
		@Test(expected = IllegalArgumentException.class)
		public void testCreateAndSave3() {
			Administrator administrator;
			RegistrationAdminForm registrationAdminForm;

			authenticate(null);

			registrationAdminForm = new RegistrationAdminForm();
			administrator = administratorService.create();

			registrationAdminForm.setName("testing");
			registrationAdminForm.setSurname("testing");
			registrationAdminForm.setEmail("testing@acme.com");

			registrationAdminForm.setUsername("admin2");
			registrationAdminForm.setPassword("admin2");
			registrationAdminForm.setSecondPassword("admin2");

			administratorService.checkPassword(registrationAdminForm);

			administrator = administratorService.convertToAdministrator(
					administrator, registrationAdminForm);
			administratorService.registerToTheSystem(administrator);

		}

	// Comprobamos que no funciona poniendo un usuario ya creado anteriormente
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateAndSave4() {
		Administrator administrator;
		RegistrationAdminForm registrationAdminForm;

		authenticate("admin");

		registrationAdminForm = new RegistrationAdminForm();
		administrator = administratorService.create();

		registrationAdminForm.setName("testing");
		registrationAdminForm.setSurname("testing");
		registrationAdminForm.setEmail("testing@acme.com");

		registrationAdminForm.setUsername("admin");
		registrationAdminForm.setPassword("admin");
		registrationAdminForm.setSecondPassword("admin");
		
		administratorService.checkPassword(registrationAdminForm);
		
		administrator = administratorService.convertToAdministrator(
				administrator, registrationAdminForm);
		administratorService.registerToTheSystem(administrator);
		administratorRepository.flush();
		
		authenticate(null);
	}
	
	// Comprobamos que no funciona poniendo contraseñas diferentes
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave5() {
		RegistrationAdminForm registrationAdminForm;

		authenticate("admin");

		registrationAdminForm = new RegistrationAdminForm();

		registrationAdminForm.setName("testing");
		registrationAdminForm.setSurname("testing");
		registrationAdminForm.setEmail("testing@acme.com");

		registrationAdminForm.setUsername("admin1000");
		registrationAdminForm.setPassword("password");
		registrationAdminForm.setSecondPassword("password2");
		
		administratorService.checkPassword(registrationAdminForm);
		
		authenticate(null);
	}

	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal() {
		authenticate("brother1");
		
		administratorService.findByPrincipal();

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal2() {
		authenticate("viewer1");
		
		administratorService.findByPrincipal();

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin loguear
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal3() {
		unauthenticate();
		
		administratorService.findByPrincipal();
	}

}
