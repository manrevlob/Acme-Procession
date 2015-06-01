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

import services.BoxService;
import utilities.AbstractTest;
import domain.Box;
import domain.Money;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class BoxServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private BoxService boxService;

	// Tests -------------------------------------------------
	
	// Creamos un nuevo box
	@Test
	public void testCreateAndSave() {
		Box box;
		Collection<Box> before;
		Collection<Box> after;
		Money price;

		authenticate("admin");

		before = boxService.findAll();

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
		
		after = boxService.findAll();

		Assert.isTrue(before.size() < after.size());

		authenticate(null);
	}
	
	// Probamos a obtener las boxes del usuario logueado
	@Test
	public void testFindByPrincipal() {
		Collection<Box> boxes;
		
		authenticate("admin");

		boxes = boxService.findByPrincipal();

		Assert.isTrue(boxes.size()==3);

		authenticate(null);
	}

}
