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
@TransactionConfiguration(defaultRollback = false)
public class CostumeServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	
	@Autowired
	private CostumeService costumeService;
	@Autowired
	private BrotherhoodService brotherhoodService;


	// Tests -------------------------------------------------

	// Creamos y guardamos un nuevo costume
	@Test
	public void testCreateAndSave() {
		CreateCostumesForm createCostumesForm;
		Brotherhood brotherhood;
		Money price;
		Collection<Costume>costumesBefore;
		Collection<Costume>costumesAfter;

		authenticate("brother1");
		
		costumesBefore = costumeService.findAll();

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

		costumesAfter = costumeService.findAll();
		
		Assert.isTrue(costumesAfter.size()>costumesBefore.size());

		authenticate(null);
	}
	
	// Probamos a obtener un costume siendo el principal
	@Test
	public void testFindOneIfPrincipal() {
		Costume costume;
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
		
		//ID del traje 1
		costume = costumeService.findOneIfPrincipal(99);

		Assert.notNull(costume);

		authenticate(null);
	}

	// Probamos a obtener un costume disponible
	@Test
	public void testFindOneIfAvailable() {
		Costume costume;
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
		
		//ID del traje 1
		costume = costumeService.findOneIfAvailable(100);

		Assert.notNull(costume.getSituation()=="available");

		authenticate(null);
	}

	// Probamos a obtener un costume alquilado
	@Test
	public void testFindOneIfRented() {
		Costume costume;
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
		
		//ID del traje 1
		costume = costumeService.findOneIfRented(99);

		Assert.notNull(costume.getSituation()=="rented");

		authenticate(null);
	}
	
	// Probamos a editar un costume
	@Test
	public void testEdit() {
		Costume costume;
		String comment;
				
		authenticate("brother1");
		
		//Id del costume2
		costume = costumeService.findOne(100);
		
		comment = costume.getComments();
		
		costume.setComments("comments30");
		
		costumeService.save(costume);
		
		costume = costumeService.findOne(100);

		Assert.isTrue(comment!=costume.getComments());

		authenticate(null);
	}
	
	// Probamos que devuelve los costumes dada una hermandad
	@Test
	public void testFindByBrotherhood() {
		Collection<Costume> costumes;
		Brotherhood brotherhood;
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
		
		// ID de la hermandad 1
		brotherhood = brotherhoodService.findOne(33);
		
		costumes = costumeService.findByBrotherhood(brotherhood);

		Assert.isTrue(costumes.size()==5);

		authenticate(null);
	}
	
	// Probamos que devuelve los costumes disponibles dada una hermandad y un rango de tallas
	@Test
	public void testFindAvailablesByBrotherhoodAndSize() {
		Collection<Costume> costumes;
		Brotherhood brotherhood;
		
		// Bigbrother de la hermandad 1 
		authenticate("brother1");
		
		// ID de la hermandad 1
		brotherhood = brotherhoodService.findOne(33);
		
		costumes = costumeService.findAvailablesByBrotherhoodAndSize(brotherhood,34,38);

		Assert.isTrue(costumes.size()==1);

		authenticate(null);
	}
	
	// Probamos a poner disponible un costume
	@Test
	public void testMarkAsAvailable() {
		Costume costume;
				
		authenticate("brother1");
		
		//Id de la costume1
		costume = costumeService.findOne(99);		
		
		costumeService.markAsAvailable(costume);
		
		costume = costumeService.findOne(99);

		Assert.isTrue(costume.getSituation()=="available");

		authenticate(null);
	}
}
