package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageFolderRepository;
import domain.MessageFolder;

@Service
@Transactional
public class MessageFolderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageFolderRepository messageFolderRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public MessageFolderService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public MessageFolder findOne(int messageFolderId) {
		MessageFolder result;

		result = messageFolderRepository.findOne(messageFolderId);

		return result;
	}

	public Collection<MessageFolder> findAll() {
		Collection<MessageFolder> result;

		result = messageFolderRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
