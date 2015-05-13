package services;

import java.sql.Date;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository messageRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private MessageFolderService messageFolderService;
	
	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Message create() {
		Message result;
		Actor sender;
		Date moment;
		long milliseconds;
		
		milliseconds = System.currentTimeMillis() - 100;
		moment = new Date(milliseconds);
		
		sender = actorService.findByPrincipal();
		
		result = new Message();
		
		result.setMoment(moment);
		result.setSender(sender);
	
		return result;
	}
	
	public Message save(Message message) {
		long milliseconds;
		
		Assert.notNull(message);
		Assert.isTrue(message.getSender() != message.getRecipient());
		
		milliseconds = System.currentTimeMillis() - 100;
		message.setMoment(new Date(milliseconds));
		
		message = messageRepository.save(message);
		
		return message;
	}
	
	public void delete(Message message) {
		Assert.notNull(message);
		
		messageRepository.delete(message);
	}
	
	public Message findOne(int messageId) {
		Message result;
		 
		result = messageRepository.findOne(messageId);
		 
		return result;
	}
	
	public Collection<Message> findAll() {
		Collection<Message> result;
		
		result = messageRepository.findAll();
		
		return result;
	}
	
	// Other business methods --------------------------------
	public Collection<Message> findByMessageFolder(MessageFolder messageFolder) {
		Collection<Message> result;
		int messageFolderId;
		
		//Assert.isTrue(actorService.findByPrincipal().getMessageFolders().contains(messageFolder));
		Assert.notNull(messageFolder);
		
		messageFolderId = messageFolder.getId();
		result = messageRepository.findByMessageFolderId(messageFolderId);
		
		return result;
	}
	
//	public Collection<Message> findByActor(){
//		Collection<Message> result;
//		Actor actor;
//		int actorId;
//	 	
//	 	actor= actorService.findByPrincipal();
//	 	actorId = actor.getId();
//
//		result = messageRepository.findByActorId(actorId);
//		
//		return result;
//	}
	
	public Message sendMessage(Message message) {
		Actor sender;
		Actor recipient;
		Collection<Message> messages;
		MessageFolder senderOutbox;
		long milliseconds;
		Date moment;
		
		milliseconds = System.currentTimeMillis() - 100;
		moment = new Date(milliseconds);
		
		Assert.notNull(message);
		
		sender = message.getSender();
		recipient = message.getRecipient();
		
		senderOutbox = messageFolderService.findOutbox(sender);
		
		receiveMessage(message, recipient);
		
		message.setMessageFolder(senderOutbox);
		message.setMoment(moment);
		messages = findByMessageFolder(senderOutbox);
		messages.add(message);
		senderOutbox.setMessages(messages);
		
		messageFolderService.save(senderOutbox);
		
		message = save(message);
		
		return message;
	}
	
	private void receiveMessage(Message message,Actor recipient) {
		Message messageReceived;
		MessageFolder recipientInbox;
		Collection<Message> messages;
		
		messageReceived = new Message();
		messageReceived.setBody(message.getBody());
		messageReceived.setMoment(message.getMoment());
		messageReceived.setSubject(message.getSubject());
		messageReceived.setRecipient(recipient);
		messageReceived.setSender(message.getSender());
		
		recipientInbox = messageFolderService.findInbox(recipient);
		messageReceived.setMessageFolder(recipientInbox);
		
		messages = findByMessageFolder(recipientInbox);
		messages.add(messageReceived);
		recipientInbox.setMessages(messages);
		
		messageFolderService.save(recipientInbox);
		
		save(messageReceived);
	}
	
	public Message createReply(Message message) {
		Message result;
		
		Assert.notNull(message);
		Assert.isTrue(actorService.findByPrincipal().equals(message.getRecipient()));
		Assert.notNull(messageFolderService.findOneIfPrincipal(message.getMessageFolder().getId()));
		
		result = create();
		
		result.setSubject("RE: " + message.getSubject());
		result.setRecipient(message.getSender());
		result.setSender(actorService.findByPrincipal());
		
		return result;
	}
	
//	public void moveMessageAroundFolders(Message message, MessageFolder newFolder) {
//		MessageFolder actual;
//		Collection<Message> messagesInActual;
//		Collection<Message> messagesInNewFolder;
//		
//		actual = message.getMessageFolder();
//		messagesInActual = findByMessageFolder(actual);
//		
//		messagesInActual.remove(message);
//		actual.setMessages(messagesInActual);
//		messageFolderService.save(actual);
//		
//		messagesInNewFolder = findByMessageFolder(newFolder);
//		messagesInNewFolder.add(message);
//		messageFolderService.save(newFolder);
//		
//		message.setMessageFolder(newFolder);
//		save(message);
//	}
	
	public boolean isRecipient(Message message, Actor actor) {
		return message.getRecipient().equals(actor);
	}
	
//	public void deleteByFolder(MessageFolder messageFolder) {
//		Collection<Message> messages;
//		
//		messages = findByMessageFolder(messageFolder);
//		
//		messageFolder.setMessages(null);
//		messageRepository.delete(messages);
//	}
	
	public void deleteOrMoveToTrashbox(Message message) {
		
		Assert.isTrue(messageFolderService.checkPrincipal(message.getMessageFolder(), actorService.findByPrincipal()));
		
		if(message.getMessageFolder().getName().toLowerCase().equals("trashbox")) {
			delete(message);
		} else {
			moveToTrashbox(message);
		}
	}
	
	public void moveToTrashbox(Message message) {
		MessageFolder actual;
		MessageFolder newFolder;
		Collection<Message> messagesInActual;
		Collection<Message> messagesInNewFolder;
		
		newFolder = messageFolderService.findTrashbox(actorService.findByPrincipal());
		actual = message.getMessageFolder();
		messagesInActual = findByMessageFolder(actual);
		
		messagesInActual.remove(message);
		actual.setMessages(messagesInActual);
		messageFolderService.save(actual);
		
		messagesInNewFolder = findByMessageFolder(newFolder);
		messagesInNewFolder.add(message);
		messageFolderService.save(newFolder);
		
		message.setMessageFolder(newFolder);
		save(message);
	}
	
	public Message findOneIfOwner(int messageId) {
		Message result;
		
		result = findOne(messageId);
		
		Assert.isTrue(messageFolderService.checkPrincipal(result.getMessageFolder(), actorService.findByPrincipal()));
		
		return result;
	}

}
