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


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class BrotherServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private BrotherService brotherService;
	@Autowired
	private BrotherhoodService brotherhoodService;

	// Tests -------------------------------------------------
	
	// Comprobamos que devuelve el usuario logueado
	@Test
	public void testFindByPrincipal() {
		Brother brother;
		// ID del author1
		int userAccountId = 2;

		authenticate("brother1");

		brother = brotherService.findByPrincipal();

		Assert.isTrue(brother.getUserAccount().getId() == userAccountId);

		authenticate(null);
	}
	
	//Comprobamos que el metodo devuelve los elementos correctos
	@Test
	public void testFindAllBrothersNotAdded() {
		Collection<Brother> brothers;
		Brotherhood brotherhood;

		authenticate("brother1");
		
		brotherhood = brotherhoodService.findOne(33);

		brothers = brotherService.findAllBrothersNotAdded(brotherhood);

		authenticate(null);

		Assert.isTrue(brothers.size() == 1);
	}
	
	//Comprobamos que el metodo devuelve los elementos correctos
		@Test
		public void testRegisterToBrotherhood() {
			Collection<Brotherhood> brotherhoods;
			Collection<Brotherhood> brotherhoodsAfter;
			Brotherhood brotherhood;
			Brother brother;

			authenticate("brother1");
			
			brotherhood = brotherhoodService.findOne(34);
			brother = brotherService.findByPrincipal();

			brotherhoods = brother.getBrotherhoods();
			
			brotherService.registerToBrotherhood(brotherhood);
			
			brotherhoodsAfter = brotherService.findByPrincipal().getBrotherhoods();

			authenticate(null);

			Assert.isTrue(brotherhoods.size()< brotherhoodsAfter.size());
		}
	
	

}
