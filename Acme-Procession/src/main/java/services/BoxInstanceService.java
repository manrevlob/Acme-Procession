package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.BoxInstanceRepository;
import domain.BoxInstance;

@Service
@Transactional
public class BoxInstanceService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BoxInstanceRepository boxInstanceRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public BoxInstanceService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public BoxInstance findOne(int boxInstanceId) {
		BoxInstance result;

		result = boxInstanceRepository.findOne(boxInstanceId);

		return result;
	}

	public Collection<BoxInstance> findAll() {
		Collection<BoxInstance> result;

		result = boxInstanceRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
