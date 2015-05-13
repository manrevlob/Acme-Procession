package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageFolderRepository;
import domain.Actor;
import domain.Message;
import domain.MessageFolder;

@Service
@Transactional
public class MessageFolderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageFolderRepository messageFolderRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------

	public MessageFolderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public MessageFolder create() {
		MessageFolder result;
		List<Message> messages = new ArrayList<Message>();

		result = new MessageFolder();
		result.setMessages(messages);

		return result;
	}

	public MessageFolder save(MessageFolder messageFolder) {
		MessageFolder result;

		Assert.notNull(messageFolder);

		result = messageFolderRepository.save(messageFolder);

		return result;
	}

	// public void delete(MessageFolder messageFolder) {
	// Collection<MessageFolder> newMessageFolders;
	//
	// Assert.notNull(messageFolder);
	//
	// messageService.deleteByFolder(messageFolder);
	//
	// //updateParentOfChildren(messageFolder);
	//
	// newMessageFolders = findPrincipalFolders();
	// newMessageFolders.remove(messageFolder);
	// actorService.findByPrincipal().setMessageFolders(newMessageFolders);
	//
	// messageFolderRepository.delete(messageFolder);
	// }

	public MessageFolder findOne(int messageFolderId) {
		Assert.notNull(messageFolderId);

		MessageFolder result;

		result = messageFolderRepository.findOne(messageFolderId);

		return result;
	}

	public Collection<MessageFolder> findAll() {
		Collection<MessageFolder> result;

		result = messageFolderRepository.findAll();

		return result;
	}

	// Other business methods --------------------------------

	public Collection<MessageFolder> findPrincipalFolders() {
		Collection<MessageFolder> result;
		int actorId;

		actorId = actorService.findByPrincipal().getId();
		result = messageFolderRepository.findByActorId(actorId);

		return result;
	}

	// public Collection<MessageFolder>
	// findPrincipalFoldersExceptThis(MessageFolder messageFolder) {
	// Collection<MessageFolder> result;
	//
	// result = findPrincipalFolders();
	// result.remove(messageFolder);
	//
	// return result;
	// }

	public MessageFolder findInbox(Actor actor) {
		MessageFolder result;
		List<MessageFolder> messageFolders;

		messageFolders = (List<MessageFolder>) findMessageFoldersByActor(actor);

		result = new MessageFolder();
		for (MessageFolder mf : messageFolders) {
			if (mf.getName().equals("Inbox")) {
				result = mf;
			}
		}
		Assert.notNull(result);

		return result;
	}

	public MessageFolder findOutbox(Actor actor) {
		MessageFolder result;
		List<MessageFolder> messageFolders;

		messageFolders = (List<MessageFolder>) findMessageFoldersByActor(actor);

		result = new MessageFolder();
		for (MessageFolder mf : messageFolders) {
			if (mf.getName().equals("Outbox")) {
				result = mf;
			}
		}
		Assert.notNull(result);

		return result;
	}

	public MessageFolder findTrashbox(Actor actor) {
		MessageFolder result;
		List<MessageFolder> messageFolders;

		messageFolders = (List<MessageFolder>) findMessageFoldersByActor(actor);

		result = new MessageFolder();
		for (MessageFolder mf : messageFolders) {
			if (mf.getName().equals("Trashbox")) {
				result = mf;
			}
		}
		Assert.notNull(result);

		return result;
	}

	public Collection<MessageFolder> findMessageFoldersByActor(Actor actor) {
		Collection<MessageFolder> result;
		int actorId;

		Assert.notNull(actor);

		actorId = actor.getId();
		result = messageFolderRepository.findByActorId(actorId);

		return result;
	}

	public MessageFolder saveOrdinaryFolder(MessageFolder messageFolder) {
		Collection<MessageFolder> actualMessageFolders;
		MessageFolder result;

		Assert.notNull(messageFolder);

		result = messageFolderRepository.save(messageFolder);

		actualMessageFolders = findPrincipalFolders();
		if (!actualMessageFolders.contains(result)) {
			actualMessageFolders.add(result);
			actorService.findByPrincipal().setMessageFolders(
					actualMessageFolders);
		}

		return result;
	}

	public MessageFolder findOneIfPrincipal(int messageFolderId) {
		MessageFolder result;

		result = findOne(messageFolderId);

		Assert.isTrue(checkPrincipal(result, actorService.findByPrincipal()));

		return result;
	}

	public boolean checkPrincipal(MessageFolder messageFolder, Actor actor) {
		boolean result;
		MessageFolder mf;

		result = false;
		mf = messageFolderRepository.checkPrincipal(messageFolder,
				actorService.findByPrincipal());

		if (mf != null) {
			result = true;
		}

		return result;
	}

}
