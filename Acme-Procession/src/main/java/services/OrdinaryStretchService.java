package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import repositories.OrdinaryStretchRepository;
import domain.OrdinaryStretch;

public class OrdinaryStretchService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private OrdinaryStretchRepository ordinaryStretchRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public OrdinaryStretchService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public OrdinaryStretch findOne(int ordinaryStretchId) {
		OrdinaryStretch result;

		result = ordinaryStretchRepository.findOne(ordinaryStretchId);

		return result;
	}

	public Collection<OrdinaryStretch> findAll() {
		Collection<OrdinaryStretch> result;

		result = ordinaryStretchRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
