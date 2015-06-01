package services.negative;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import services.ActorService;
import services.MessageFolderService;
import services.MessageService;
import utilities.AbstractTest;
import domain.Message;
import domain.MessageFolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MessageServiceTestNegative extends AbstractTest {
	// Service under test ------------------------------------
	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageFolderService messageFolderService;
	@Autowired
	private ActorService actorService;

	// Tests -------------------------------------------------

	// Comprobamos que no podemos buscar todos los mensajes de una carpeta que
	// no existe
	@Test(expected = IllegalArgumentException.class)
	public void testFindByMessageFolderIdNegative() {
		MessageFolder messageFolder;

		authenticate("admin");

		// ID de un messageFolder que no existe
		messageFolder = messageFolderService.findOne(100000000);

		messageService.findByMessageFolder(messageFolder);

		authenticate(null);
	}
	
	// Tratamos de enviar un mensaje con el recipient y el sender iguales
	@Test(expected = IllegalArgumentException.class)
	public void testSendMessage() {
		Message message;
		
		authenticate("admin");
		
		message = messageService.create();
		
		message.setSubject("Test");
		message.setBody("Test");
		// ID del admin, nosotros mismos, al que trataremos de enviar el mensaje
		message.setRecipient(actorService.findOne(35));
		
		messageService.sendMessage(message);
		
		unauthenticate();
	}
	
	// Tratamos de enviar un mensaje sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testSendMessage2() {
		Message message;
		
		unauthenticate();
		
		message = messageService.create();
		
		message.setSubject("Test");
		message.setBody("Test");
		// ID del admin, al que enviaremos el mensaje
		message.setRecipient(actorService.findOne(35));
		
		messageService.sendMessage(message);
	}

	// Comprobamos que no podemos responder mensajes si el propietario del
	// mensaje no es el que envia el mensaje
	@Test(expected = IllegalArgumentException.class)
	public void testCreateReplyNegative() {
		Message messageToReply;

		// Brother 1 es el destinatario del mensaje uno y, por tanto, el único
		// que puede responder
		authenticate("brother1");

		// ID del mensaje 1, que no es del usuario
		messageToReply = messageService.findOne(43);

		messageService.createReply(messageToReply);

		unauthenticate();
	}

	// Comprobamos que un usuario no puede mandar a la papelera un mensaje del
	// cual no es propietario
	@Test(expected = IllegalArgumentException.class)
	public void testSendToTrashboxNegative() {
		Message message;

		// Admin es el propietario del mensaje
		authenticate("brother2");

		// ID del mensaje 1
		message = messageService.findOne(43);

		messageService.deleteOrMoveToTrashbox(message);

		unauthenticate();
	}
}
