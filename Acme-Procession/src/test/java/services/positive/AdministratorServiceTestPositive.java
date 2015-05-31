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

import services.AdministratorService;
import utilities.AbstractTest;
import domain.Administrator;
import forms.RegistrationAdminForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class AdministratorServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private AdministratorService administratorService;

	// Tests -------------------------------------------------
	
	// Registramos un nuevo administrador
	@Test
	public void testCreateAndSave() {
		Administrator administrator;
		RegistrationAdminForm registrationAdminForm;
		Collection<Administrator> before;
		Collection<Administrator> after;

		authenticate("admin");

		before = administratorService.findAll();

		registrationAdminForm = new RegistrationAdminForm();
		administrator = administratorService.create();

		registrationAdminForm.setName("testing");
		registrationAdminForm.setSurname("testing");
		registrationAdminForm.setEmail("testing@acme.com");

		registrationAdminForm.setUsername("admin5");
		registrationAdminForm.setPassword("admin5");
		registrationAdminForm.setSecondPassword("admin5");

		administratorService.checkPassword(registrationAdminForm);
		
		administrator = administratorService.convertToAdministrator(administrator, registrationAdminForm);
		administratorService.registerToTheSystem(administrator);

		after = administratorService.findAll();

		Assert.isTrue(before.size() < after.size());

		authenticate(null);
	}
	
	// Probamos a encontrar el usuario logeado
	@Test
	public void testFindByPrincipal() {
		Administrator administrator;
		// ID del admin
		int userAccountId = 1;

		authenticate("admin");

		administrator = administratorService.findByPrincipal();

		Assert.isTrue(administrator.getUserAccount().getId() == userAccountId);

		authenticate(null);
	}

}
