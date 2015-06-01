package services.negative;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import services.MessageFolderService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MessageFolderServiceTestNegative extends AbstractTest {
	
	// Service under test ------------------------------------
	@Autowired
	private MessageFolderService messageFolderService;
		
	// Tests -------------------------------------------------
	
	// Intentamos obtener las carpetas del usuario logueado sin estarlo
	@Test(expected = IllegalArgumentException.class)
	public void testFindPrincipalFolders() {
		unauthenticate();
		
		messageFolderService.findPrincipalFolders();
	}
	
	// Comprobamos que no podemos obtener una carpeta de un usuario que no es dueño
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipalNegative() {
		authenticate("admin");
		
		// ID del inbox 2, que no es del administrador
		messageFolderService.findOneIfPrincipal(15);
		
		unauthenticate();
	}
	
	// Comprobamos que no podemos obtener una carpeta de un usuario sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testFindOneIfPrincipalNegative2() {
		unauthenticate();
		
		// ID del inbox 2
		messageFolderService.findOneIfPrincipal(11);
	}
	
}
