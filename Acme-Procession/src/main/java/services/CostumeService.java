package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CostumeRepository;
import domain.Costume;

@Service
@Transactional
public class CostumeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private CostumeRepository costumeRepository;

	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------

	public CostumeService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Costume findOne(int costumeId) {
		Costume result;

		result = costumeRepository.findOne(costumeId);

		return result;
	}

	public Collection<Costume> findAll() {
		Collection<Costume> result;

		result = costumeRepository.findAll();

		return result;
	}
	// Other business methods -------------------------------------------------

}
