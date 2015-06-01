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

import services.ActorService;
import services.MessageFolderService;
import services.MessageService;
import utilities.AbstractTest;
import domain.Message;
import domain.MessageFolder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class MessageServiceTestPositive extends AbstractTest {
	// Service under test ------------------------------------
	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageFolderService messageFolderService;
	@Autowired
	private ActorService actorService;
		
	// Tests -------------------------------------------------
	
	// Obtenemos todos los mensajes de una carpeta
	@Test
	public void testFindByMessageFolderId() {
		Collection<Message> messagesInFolder;
		MessageFolder messageFolder;
		
		authenticate("admin");
		
		// ID del outbox del admin
		messageFolder = messageFolderService.findOne(10);
		
		messagesInFolder = messageService.findByMessageFolder(messageFolder);
		
		Assert.isTrue(messagesInFolder.size() == 1);
		
		authenticate(null);
	}
	
	// Enviamos un mensaje
	@Test
	public void testSendMessage() {
		// Este test ya prueba que funcione tanto el citado servicio 'SendMessage'
		// como el servicio 'ReceiveMessage' y los CRUD methods 'create' y 'save'
		Message message;
		Collection<Message> before;
		Collection<Message> after;
		
		authenticate("brother1");
		
		before = messageService.findAll();
		
		message = messageService.create();
		
		message.setSubject("Test");
		message.setBody("Test");
		// ID del admin, al que enviaremos el mensaje
		message.setRecipient(actorService.findOne(35));
		
		message = messageService.sendMessage(message);
		
		after = messageService.findAll();
		
		Assert.isTrue(before.size() < after.size());
		Assert.isTrue(before.size() + 2 == after.size());
		
		unauthenticate();
	}
	
	// Respondemos a un mensaje
	@Test
	public void testCreateReply() {
		Message messageToReply;
		Message messageResponded;
		
		// Brother 1 es el destinatario del mensaje uno y, por tanto, el único
		// que puede responder
		authenticate("brother1");
		
		// ID del mensaje 2
		messageToReply = messageService.findOne(44);
		
		messageResponded = messageService.createReply(messageToReply);
		
		Assert.isTrue(messageToReply.getRecipient() == messageResponded.getSender());
		Assert.isTrue(messageToReply.getSender() == messageResponded.getRecipient());
		Assert.isTrue(messageToReply.getMoment() != messageResponded.getMoment());
		Assert.isTrue(messageToReply.getMessageFolder() != messageResponded.getMessageFolder());
		
		unauthenticate();
	}
	
	// Obtenemos si el destinatario de un mensaje es el correcto
	@Test
	public void testIsRecipient() {
		Message message;
		boolean result;
		
		// Pilgrim 1 es el destinatario
		authenticate("brother1");
		
		// ID del mensaje 2
		message = messageService.findOne(44);
		
		result = messageService.isRecipient(message, actorService.findByPrincipal());
		
		// ID del mensaje 1 y del trashbox del administrador respectivamente
		Assert.isTrue(result);
		
		unauthenticate();
	}
	
	// Enviamos un mensaje a la papelera
	@Test
	public void testSendToTrashbox() {
		Message message;
		
		// Admin es el propietario del mensaje
		authenticate("admin");
		
		// ID del mensaje 1
		message = messageService.findOne(43);
		
		messageService.deleteOrMoveToTrashbox(message);
		
		// ID del mensaje 1 y del trashbox del administrador respectivamente
		Assert.isTrue(messageService.findOne(43).getMessageFolder() == messageFolderService.findOne(11));
		
		unauthenticate();
	}
}
