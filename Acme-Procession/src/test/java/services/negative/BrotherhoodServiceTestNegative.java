package services.negative;

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
@TransactionConfiguration(defaultRollback = true)
public class BrotherhoodServiceTestNegative extends AbstractTest {

	// Service under test ------------------------------------

	@Autowired
	private BrotherhoodService brotherhoodService;
	@Autowired
	private BrotherService brotherService;

	// Tests -------------------------------------------------

	// Intentamos crear una nueva hermandad sin tener permisos para ello
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative1() {
		Brotherhood brotherhood;

		authenticate("brother5");

		brotherhood = brotherhoodService.create();

		brotherhood.setName("Test");
		brotherhood.setFoundationYear(1900);
		brotherhood.setHistory("Test");

		brotherhoodService.save(brotherhood);

		unauthenticate();
	}

	// Intentamos crear una nueva hermandad siendo administrador
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative2() {
		Brotherhood brotherhood;

		authenticate("admin");

		brotherhood = brotherhoodService.create();

		brotherhood.setName("Test");
		brotherhood.setFoundationYear(1900);
		brotherhood.setHistory("Test");

		brotherhoodService.save(brotherhood);

		unauthenticate();
	}

	// Intentamos crear una nueva hermandad siendo espectador
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative3() {
		Brotherhood brotherhood;

		authenticate("viewer");

		brotherhood = brotherhoodService.create();

		brotherhood.setName("Test");
		brotherhood.setFoundationYear(1900);
		brotherhood.setHistory("Test");

		brotherhoodService.save(brotherhood);

		unauthenticate();
	}

	// Intentamos crear una nueva hermandad sin estar logeados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveNegative4() {
		Brotherhood brotherhood;

		unauthenticate();

		brotherhood = brotherhoodService.create();

		brotherhood.setName("Test");
		brotherhood.setFoundationYear(1900);
		brotherhood.setHistory("Test");

		brotherhoodService.save(brotherhood);
	}

	// Intentamos acceder a una hermandad sin ser hermano mayor de la misma
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipalNegative1() {
		authenticate("brother1");

		// ID de la brotherhood 2
		brotherhoodService.findOneIfPrincipal(34);

		unauthenticate();
	}

	// Intentamos acceder a una hermandad siendo administrador
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipalNegative2() {
		authenticate("admin");

		// ID de la brotherhood 2
		brotherhoodService.findOneIfPrincipal(34);

		unauthenticate();
	}

	// Intentamos acceder a una hermandad siendo espectador
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipalNegative3() {
		authenticate("admin");

		// ID de la brotherhood 2
		brotherhoodService.findOneIfPrincipal(34);

		unauthenticate();
	}

	// Intentamos acceder a una hermandad sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipalNegative() {
		unauthenticate();

		// ID de la brotherhood 2
		brotherhoodService.findOneIfPrincipal(34);
	}

	// Intentamos acceder a nuestras hermandades siendo administrador
	@Test(expected = IllegalArgumentException.class)
	public void testFindMinesNegative1() {
		authenticate("admin");

		brotherhoodService.findMines();

		unauthenticate();
	}

	// Intentamos acceder a nuestras hermandades siendo espectador
	@Test(expected = IllegalArgumentException.class)
	public void testFindMinesNegative2() {
		authenticate("viewer1");

		brotherhoodService.findMines();

		unauthenticate();
	}

	// Intentamos acceder a nuestras hermandades sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindMinesNegative3() {
		unauthenticate();

		brotherhoodService.findMines();
	}

	// Accedemos a las hermandades de las que somos hermanos mayores sin ser
	// hermano mayor
	@Test(expected = IllegalArgumentException.class)
	public void testFindOwnsNegative1() {
		authenticate("brother5");

		brotherhoodService.findOwns();

		unauthenticate();
	}

	// Accedemos a las hermandades de las que somos hermanos mayores siendo
	// administrador
	@Test(expected = IllegalArgumentException.class)
	public void testFindOwnsNegative2() {
		authenticate("admin");

		brotherhoodService.findOwns();

		unauthenticate();
	}

	// Accedemos a las hermandades de las que somos hermanos mayores siendo
	// espectador
	@Test(expected = IllegalArgumentException.class)
	public void testFindOwnsNegative3() {
		authenticate("viewer1");

		brotherhoodService.findOwns();

		unauthenticate();
	}

	// Accedemos a las hermandades de las que somos hermanos mayores sin estar
	// logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindOwnsNegative4() {
		unauthenticate();

		brotherhoodService.findOwns();
	}

	// Intentamos añadir un nuevo hermano mayor a una hermandad de la que NO
	// somos hermanos
	// mayores
	@Test(expected = IllegalArgumentException.class)
	public void testAddBrotherNegative1() {
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

		authenticate("brother5");

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

	// Intentamos añadir un nuevo hermano mayor que NO es hermano de la
	// hermandad a una hermandad de la que somos hermanos
	// mayores
	@Test(expected = IllegalArgumentException.class)
	public void testAddBrotherNegative2() {
		AddBigBrotherForm addBigBrotherForm;
		Brotherhood brotherhood;
		Brother brotherToAdd;
		int before;
		int after;

		addBigBrotherForm = new AddBigBrotherForm();

		authenticate("brother5");

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
