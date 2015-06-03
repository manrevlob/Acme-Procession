package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.BrotherhoodService;
import services.CostumeService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Costume;
import domain.Money;
import forms.CreateCostumesForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CostumeServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private CostumeService costumeService;
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	// Tests -------------------------------------------------
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave() {
		CreateCostumesForm createCostumesForm;
		Brotherhood brotherhood;
		Money price;

		authenticate("viewer1");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		
		createCostumesForm = new CreateCostumesForm();
		createCostumesForm.setBrotherhood(brotherhood);
		
		createCostumesForm.setComments("comments");
		createCostumesForm.setSize(38);
		createCostumesForm.setStatus("new");
		createCostumesForm.setNumberOfCostumes(2);
		
		price = new Money();
		price.setAmount(32);
		price.setCurrency("Euro");
		
		createCostumesForm.setRentalPrice(price);
		createCostumesForm.setSalePrice(price);

		costumeService.saveAll(createCostumesForm);

		unauthenticate();
	}

	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave2() {
		CreateCostumesForm createCostumesForm;
		Brotherhood brotherhood;
		Money price;

		authenticate("admin");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		
		createCostumesForm = new CreateCostumesForm();
		createCostumesForm.setBrotherhood(brotherhood);
		
		createCostumesForm.setComments("comments");
		createCostumesForm.setSize(38);
		createCostumesForm.setStatus("new");
		createCostumesForm.setNumberOfCostumes(2);
		
		price = new Money();
		price.setAmount(32);
		price.setCurrency("Euro");
		
		createCostumesForm.setRentalPrice(price);
		createCostumesForm.setSalePrice(price);

		costumeService.saveAll(createCostumesForm);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar autenticados 
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave3() {
		CreateCostumesForm createCostumesForm;
		Brotherhood brotherhood;
		Money price;

		authenticate(null);

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		
		createCostumesForm = new CreateCostumesForm();
		createCostumesForm.setBrotherhood(brotherhood);
		
		createCostumesForm.setComments("comments");
		createCostumesForm.setSize(38);
		createCostumesForm.setStatus("new");
		createCostumesForm.setNumberOfCostumes(2);
		
		price = new Money();
		price.setAmount(32);
		price.setCurrency("Euro");
		
		createCostumesForm.setRentalPrice(price);
		createCostumesForm.setSalePrice(price);

		costumeService.saveAll(createCostumesForm);

	}
	
	// Comprobamos que no funciona si no somos BigBrother de la brotherhood
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave4() {
		CreateCostumesForm createCostumesForm;
		Brotherhood brotherhood;
		Money price;
		
		// ID del brother 2(no bigBrother)
		authenticate("brother2");

		// ID del brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		
		createCostumesForm = new CreateCostumesForm();
		createCostumesForm.setBrotherhood(brotherhood);
		
		createCostumesForm.setComments("comments");
		createCostumesForm.setSize(38);
		createCostumesForm.setStatus("new");
		createCostumesForm.setNumberOfCostumes(2);
		
		price = new Money();
		price.setAmount(32);
		price.setCurrency("Euro");
		
		createCostumesForm.setRentalPrice(price);
		createCostumesForm.setSalePrice(price);

		costumeService.saveAll(createCostumesForm);

		unauthenticate();
	}

	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal() {
		
		// Bigbrother de la hermandad 1 
		authenticate("viewer1");
		
		//ID del traje 1
		costumeService.findOneIfPrincipal(99);

		authenticate(null);
	}

	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal2() {
		
		authenticate("admin");
		
		//ID del traje 1
		costumeService.findOneIfPrincipal(99);

		authenticate(null);
	}
		
	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal3() {
		
		authenticate(null);
		
		//ID del traje 1
		costumeService.findOneIfPrincipal(99);	
	}
	
	// Comprobamos que no funciona si el costume no es nuestro
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipal4() {
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
		
		//ID del traje 1
		costumeService.findOneIfPrincipal(104);

		authenticate(null);
	}
	
	// Comprobamos que no funciona si el costume no esta disponible
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfAvailable4() {
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
		
		//ID del traje 1
		costumeService.findOneIfAvailable(99);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfRented() {
		
		authenticate("viewer1");
		
		//ID del traje 1
		costumeService.findOneIfRented(99);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfRented2() {
		
		authenticate("admin");
		
		//ID del traje 1
		costumeService.findOneIfRented(99);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfRented3() {

		authenticate(null);
		
		//ID del traje 1
		costumeService.findOneIfRented(99);
		
	}
	
	// Comprobamos que no funciona si el costume no esta alquilado
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfRented4() {
		
		authenticate("admin");
		
		//ID del traje 1
		costumeService.findOneIfRented(100);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testEdit() {
		Costume costume;
				
		authenticate("viewer1");
		
		//Id del costume2
		costume = costumeService.findOne(100);
		
		costume.setComments("comments30");
		
		costumeService.save(costume);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testEdit2() {
		Costume costume;
				
		authenticate("admin");
		
		//Id del costume2
		costume = costumeService.findOne(100);
		
		costume.setComments("comments30");
		
		costumeService.save(costume);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar logueado
	@Test(expected = IllegalArgumentException.class)
	public void testEdit3() {
		Costume costume;
				
		authenticate(null);
		
		//Id del costume2
		costume = costumeService.findOne(100);
		
		costume.setComments("comments30");
		
		costumeService.save(costume);		
	}
	
	// Comprobamos que no funciona con un costume que no es nuestro
	@Test(expected = IllegalArgumentException.class)
	public void testEdit4() {
		Costume costume;
				
		authenticate("brother1");
		
		//Id del costume2
		costume = costumeService.findOne(106);
		
		costume.setComments("comments30");
		
		costumeService.save(costume);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBrotherhood() {
		Brotherhood brotherhood;
		
		authenticate("admin");
		
		// ID de la hermandad 1
		brotherhood = brotherhoodService.findOne(33);
		
		costumeService.findByBrotherhood(brotherhood);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBrotherhood2() {
		Brotherhood brotherhood;
		
		authenticate("viewer1");
		
		// ID de la hermandad 1
		brotherhood = brotherhoodService.findOne(33);
		
		costumeService.findByBrotherhood(brotherhood);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBrotherhood3() {
		Brotherhood brotherhood;
		
		authenticate(null);
		
		// ID de la hermandad 1
		brotherhood = brotherhoodService.findOne(33);
		
		costumeService.findByBrotherhood(brotherhood);
	}
	
	// Comprobamos que no funciona si no se es bigBrother de la brotherhood
	@Test(expected = IllegalArgumentException.class)
	public void testFindByBrotherhood4() {
		Brotherhood brotherhood;
		
		// BigBrother de la brotherhood 2 no la 1
		authenticate("brother2");
		
		// ID de la hermandad 1
		brotherhood = brotherhoodService.findOne(33);
		
		costumeService.findByBrotherhood(brotherhood);

		authenticate(null);
	}

	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testMarkAsAvailable() {
		Costume costume;
				
		authenticate("viewer1");
		
		//Id de la costume1
		costume = costumeService.findOne(99);		
		
		costumeService.markAsAvailable(costume);

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de admin
	@Test(expected = IllegalArgumentException.class)
	public void testMarkAsAvailable2() {
		Costume costume;
				
		authenticate("admin");
		
		//Id de la costume1
		costume = costumeService.findOne(99);		
		
		costumeService.markAsAvailable(costume);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testMarkAsAvailable3() {
		Costume costume;
				
		authenticate(null);
		
		//Id de la costume1
		costume = costumeService.findOne(99);		
		
		costumeService.markAsAvailable(costume);
	}
	
	// Comprobamos que no funciona si no se es bigbrother
	@Test(expected = IllegalArgumentException.class)
	public void testMarkAsAvailable4() {
		Costume costume;
				
		// bigbrother de brotherhood2, no 1
		authenticate("brother2");
		
		//Id de la costume1 de la brotherhood1
		costume = costumeService.findOne(99);		
		
		costumeService.markAsAvailable(costume);

		authenticate(null);
	}
	

}
