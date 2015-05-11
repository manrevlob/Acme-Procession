package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FloatStretchRepository;
import domain.FloatStretch;

@Service
@Transactional
public class FloatStretchService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private FloatStretchRepository floatStretchRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public FloatStretchService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public FloatStretch findOne(int floatStretchId) {
		FloatStretch result;

		result = floatStretchRepository.findOne(floatStretchId);

		return result;
	}

	public Collection<FloatStretch> findAll() {
		Collection<FloatStretch> result;

		result = floatStretchRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
