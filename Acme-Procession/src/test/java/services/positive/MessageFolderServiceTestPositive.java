package services.positive;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import services.MessageFolderService;
import utilities.AbstractTest;
import domain.MessageFolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class MessageFolderServiceTestPositive extends AbstractTest {
	
	// Service under test ------------------------------------
	@Autowired
	private MessageFolderService messageFolderService;
		
	// Tests -------------------------------------------------
	
	// Obtenemos las carpetas de un usuario logueado
	@Test
	public void testFindPrincipalFolders() {
		Collection<MessageFolder> messageFolders;
		
		authenticate("brother1");
		
		messageFolders = messageFolderService.findPrincipalFolders();
		
		Assert.isTrue(messageFolders.size() == 3);
		
		unauthenticate();
	}
	
	// Obtenemos la carpeta de un usuario si es propietario
	@Test
	public void testFindOneIfPrincipal() {
		MessageFolder messageFolder;
		
		authenticate("admin");
		
		//ID del inbox 1
		messageFolder = messageFolderService.findOneIfPrincipal(9);
		
		Assert.notNull(messageFolder);
		
		unauthenticate();
	}
	
}
