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
import services.BrotherhoodService;
import utilities.AbstractTest;
import domain.Brother;
import domain.Brotherhood;
import forms.AddBigBrotherForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class BrotherhoodServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private BrotherService brotherService;

	// Tests -------------------------------------------------

	// Creamos una nueva hermandad
	@Test
	public void testCreateAndSave() {
		Brotherhood brotherhood;
		Collection<Brotherhood> before;
		Collection<Brotherhood> after;

		authenticate("brother1");

		before = brotherhoodService.findAll();

		brotherhood = brotherhoodService.create();

		brotherhood.setName("Test");
		brotherhood.setFoundationYear(1900);
		brotherhood.setHistory("Test");

		brotherhoodService.save(brotherhood);

		after = brotherhoodService.findAll();

		unauthenticate();

		Assert.isTrue(before.size() < after.size());
	}

	// Accedemos a una hermandad de la que somos hermano mayor
	@Test
	public void testFindOneIfPrincipal() {
		Brotherhood brotherhood;

		authenticate("brother1");

		// ID de la brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);

		unauthenticate();

		Assert.notNull(brotherhood);
	}

	// Accedemos a nuestras hermandades
	@Test
	public void testFindMines() {
		Collection<Brotherhood> brotherhoods;

		authenticate("brother2");

		brotherhoods = brotherhoodService.findMines();

		unauthenticate();

		Assert.isTrue(brotherhoods.size() == 2);
	}

	// Accedemos a las hermandades de las que somos hermanos mayores
	@Test
	public void testFindOwns() {
		Collection<Brotherhood> brotherhoods;

		authenticate("brother1");

		brotherhoods = brotherhoodService.findOwns();

		unauthenticate();

		Assert.isTrue(brotherhoods.size() == 1);
	}

	// Añadimos un nuevo hermano mayor a una hermandad de la que somos hermanos
	// mayores
	@Test
	public void testAddBrother() {
		AddBigBrotherForm addBigBrotherForm;
		Brotherhood brotherhood;
		Brother brotherToAdd;
		int before;
		int after;

		addBigBrotherForm = new AddBigBrotherForm();

		authenticate("brother2");

		brotherToAdd = brotherService.findByPrincipal();
		addBigBrotherForm.setBrother(brotherToAdd);

		unauthenticate();

		authenticate("brother1");

		// ID de la brotherhood 1
		brotherhood = brotherhoodService.findOneIfPrincipal(33);
		addBigBrotherForm.setBrotherhood(brotherhood);

		before = brotherhood.getBigBrothers().size();

		brotherhoodService.addBrother(addBigBrotherForm);

		brotherhood = brotherhoodService.findOneIfPrincipal(33);

		after = brotherhood.getBigBrothers().size();

		unauthenticate();

		Assert.isTrue(before < after);
	}

}
