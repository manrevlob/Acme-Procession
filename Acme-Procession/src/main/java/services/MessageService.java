package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MessageRepository messageRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public MessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

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
	// Other business methods -------------------------------------------------

}
