package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.BoxRepository;
import domain.Box;

@Service
@Transactional
public class BoxService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxRepository boxRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BoxService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Box findOne(int boxId) {
		Box result;

		result = boxRepository.findOne(boxId);

		return result;
	}

	public Collection<Box> findAll() {
		Collection<Box> result;

		result = boxRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
