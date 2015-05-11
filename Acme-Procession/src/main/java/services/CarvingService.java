package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CarvingRepository;
import domain.Carving;

@Service
@Transactional
public class CarvingService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CarvingRepository carvingRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CarvingService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Carving findOne(int carvingId) {
		Carving result;

		result = carvingRepository.findOne(carvingId);

		return result;
	}

	public Collection<Carving> findAll() {
		Collection<Carving> result;

		result = carvingRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
