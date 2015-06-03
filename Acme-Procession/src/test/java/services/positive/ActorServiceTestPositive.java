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

import services.ActorService;
import utilities.AbstractTest;
import domain.Actor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ActorServiceTestPositive extends AbstractTest {

	// Service under test ------------------------------------
	@Autowired
	private ActorService actorService;

	// Tests -------------------------------------------------
	// Probamos que el usuario logeado tenga privilegios de administrador
	@Test
	public void testIsAdministrator() {
		boolean bool;

		authenticate("admin");

		bool = actorService.isAdministrator();

		Assert.isTrue(bool);

		authenticate(null);
	}

	// Probamos que el usuario logeado tenga privilegios de brother
	@Test
	public void testIsBrother() {
		boolean bool;

		authenticate("brother1");

		bool = actorService.isBrother();

		Assert.isTrue(bool);

		authenticate(null);
	}

	// Probamos que el usuario logeado tenga privilegios de viewer
	@Test
	public void testIsViewer() {
		boolean bool;

		authenticate("viewer1");

		bool = actorService.isViewer();

		Assert.isTrue(bool);

		authenticate(null);
	}

	// Probamos que el usuario logeado tenga privilegios de brother o viewer
	@Test
	public void testIsCustomer() {
		boolean bool;

		authenticate("brother1");

		bool = actorService.isCustomer();

		Assert.isTrue(bool);

		authenticate(null);
	}

	// Probamos que se devuelva correctamente el usuario logueado
	@Test
	public void testFindByPrincipal() {
		Actor actor;

		authenticate("brother1");

		actor = actorService.findByPrincipal();

		Assert.notNull(actor);

		authenticate(null);
	}

	// Probamos a encontrar todos los usuarios del sistema salvo nosotros mismos
	// este método se usa para a la hora de enviar un mensaje traernos los
	// posibles destinatarios
	@Test
	public void testFindAllExceptMe() {
		Collection<Actor> actors;

		authenticate("brother1");

		actors = actorService.findAllExceptMe();

		Assert.isTrue(actors.size() == 7);

		authenticate(null);
	}

}
