package services.negative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import services.BoxService;
import utilities.AbstractTest;
import domain.Box;
import domain.Money;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BoxServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private BoxService boxService;
	
	// Tests -------------------------------------------------
	
	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave() {
		Box box;
		Money price;

		authenticate("brother1");

		box = boxService.create();

		box.setDescription("description");
		box.setLocality("locality");
		box.setLocation("location");
		box.setName("name");
		box.setNumberOfChairs(14);
		
		price = new Money();
		price.setAmount(14);
		price.setCurrency("Euro");
		
		box.setPrice(price);
		
		boxService.save(box);

		authenticate(null);
	}

	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSave2() {
		Box box;
		Money price;

		authenticate("viewer1");

		box = boxService.create();

		box.setDescription("description");
		box.setLocality("locality");
		box.setLocation("location");
		box.setName("name");
		box.setNumberOfChairs(14);
		
		price = new Money();
		price.setAmount(14);
		price.setCurrency("Euro");
		
		box.setPrice(price);
		
		boxService.save(box);

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin estar autenticados 
		@Test(expected = IllegalArgumentException.class)
		public void testCreateAndSave3() {
			Box box;
			Money price;

			authenticate(null);

			box = boxService.create();

			box.setDescription("description");
			box.setLocality("locality");
			box.setLocation("location");
			box.setName("name");
			box.setNumberOfChairs(14);
			
			price = new Money();
			price.setAmount(14);
			price.setCurrency("Euro");
			
			box.setPrice(price);
			
			boxService.save(box);

		}	

	// Comprobamos que no funciona con el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal() {
		authenticate("brother1");
		
		boxService.findByPrincipal();

		authenticate(null);
	}
	
	// Comprobamos que no funciona con el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal2() {
		authenticate("viewer1");
		
		boxService.findByPrincipal();

		authenticate(null);
	}
	
	// Comprobamos que no funciona sin loguear
	@Test(expected = IllegalArgumentException.class)
	public void testFindByPrincipal3() {
		unauthenticate();
		
		boxService.findByPrincipal();
	}

}
