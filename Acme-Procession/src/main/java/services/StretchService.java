package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.StretchRepository;
import domain.Procession;
import domain.Stretch;

@Service
@Transactional
public class StretchService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StretchRepository stretchRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public StretchService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Stretch findOne(int stretchId) {
		Stretch result;

		result = stretchRepository.findOne(stretchId);

		return result;
	}

	public Collection<Stretch> findAll() {
		Collection<Stretch> result;

		result = stretchRepository.findAll();

		return result;
	}
	
	// Other business methods -------------------------------------------------

	public Collection<Stretch> findAvailables(Procession procession) {
		Collection<Stretch> result;
		
		result = stretchRepository.findAvailableStretches(procession);
		
		return result;
	}

}
